import { Component, OnInit, ElementRef, ViewChild, inject, Injectable } from '@angular/core';
import { AccountServiceService } from '../account-service/account-service.service';
import { Account, AccountLine, AmountOfMoney, AccountDateSum, FileBase64, Market, MarketValue, AccountValue, AccountDatePercentage, AccountDataResult, CombinedData, MarketDataResult, MarketPercentage } from '../model/account-model';
import { formatDate } from '@angular/common';
import { animate } from '@angular/animations';
import { Chart, registerables, ChartDataset, ChartOptions } from 'chart.js';
import 'chartjs-adapter-moment';
import { HttpClient } from '@angular/common/http';
import { FormArray, ReactiveFormsModule, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { forkJoin, Observable } from 'rxjs';
import { HeaderComponent } from "../header/header.component";
import { LeftSidebarComponent } from "../left-sidebar/left-sidebar.component";
import { AuthService } from '../auth-service/auth.service';



Chart.register(...registerables);


@Component({
  selector: 'app-account-list',
  imports: [ReactiveFormsModule, HeaderComponent, LeftSidebarComponent],
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})

@Injectable({
  providedIn: 'root' // <-- C'est ici qu'il faut s'assurer que c'est bien configuré
})

export class AccountListComponent implements OnInit{


  selectedFiles: File[] = [];
	accounts: Account[] = [];
  accountTypes: string[] = [];
  sumDates: AccountDateSum[] | AccountDatePercentage[] = [];
  accountValues: AccountValue[] = [];
  marketValues: MarketValue[] = [];
  marketIndices: Market[] | MarketPercentage[] = [];
  @ViewChild('myChart') myChartCanvas!: ElementRef;
  chart: any;
  isSubmitted = false; // Nouvelle variable pour suivre l'état

  // problème avec startdate systemantiqument non remplie bien que saisie !!!!!!!!!!!!!!!!!!!????????????????????????????????
  startDate = new FormControl('2020-01-01');
  end = new FormControl('2020-01-01');
  typeAccount = new FormControl('');
  endDate = new FormControl('2030-01-01');
  percentageDisplay = new FormControl(false);
  //private fb = inject(FormBuilder);
  myForm!: FormGroup;
  curb1Display = new FormControl(true);
  curb2Display = new FormControl(true);
  userAdmin: boolean | null = null;

	constructor(private accountService: AccountServiceService, private http:  HttpClient, private fb: FormBuilder, private snackBar: MatSnackBar, private authService: AuthService){}

		 ngOnInit(): void {


       this.userAdmin = this.authService.hasRole('ROLE_ADMIN');

       console.log('!!!!!!!!!!!!!!!!!!!!!! on est sur un admin !!!!!!!!!!!!!!!!!!!!!!! : ' + this.userAdmin);

       this.accountService.getAccountType().subscribe((data) => {

        this.accountTypes = data;

        this.accountTypes.push('*All');


        console.log(this.accountTypes);

        this.myForm = this.fb.group({

          startDate: this.startDate,
          end: this.end,
          endDate: this.endDate,
          percentageDisplay: this.percentageDisplay,
          typeAccounts:  this.fb.array([]),
          curb1Display: this.curb1Display,
          curb2Display: this.curb2Display

         });


         this.accountTypes.map((t) => {

          let at = this.fb.group({ name: `${t}`, checked: false });

          this.typeAccounts.push(at);

        });


        console.log('!!!!!!!!!!!!!!!!!!!!!! on est sur un admin !!!!!!!!!!!!!!!!!!!!!!! : ' + this.userAdmin);

         console.log(this.myForm);


         const savedState =  localStorage.getItem('formState');
        if (savedState) {
          const state = JSON.parse(savedState);
          this.isSubmitted = state.isSubmitted;

          this.myForm.setValue(state.formData);

          if(this.isSubmitted){
             this.onSubmit()
          }

        }

        this.myForm.valueChanges.subscribe(value => {
          this.saveState();
        });

      });


			/*	console.log('on est là');

        this.accountService.getAccounts().subscribe((data) => {

          console.log(data);

          this.accounts = data;

          console.log(this.accounts);

        });

        let start: string = '2000-01-01';

        let end: string = '2050-01-01';


        this.accountService.getSumDate(start, end).subscribe((data) => {

          console.log(data);

          this.sumDates = data;

          this.createChart(this.sumDates);

          console.log(this.accounts);

        });*/

	}

  get typeAccounts() {
    return this.myForm.get('typeAccounts') as FormArray;
  }


    createChart(data: AccountValue[], dataMarket: MarketValue[], booleanPercentage: boolean): void {


      const dates = data.map((d) => {

        let dateStr:string = `${d.year}-${d.month}-01`;

        console.log(dateStr);

        return new Date(dateStr)
      });


      const amounts = data.map((d) => d.value);

      const marketIndices = dataMarket.map((m) => m.value);


      const marketIndices2 = dates.map(d => {

        const marketForDate = dataMarket.find(m => {
          return m.year === d.getFullYear() && m.month === d.getMonth() + 1;
        });

        return marketForDate ? marketForDate.value : 0;
      });


      let minValue;
      let maxValue;
      let minValueMarket;
      let maxValueMarket;
      let booleanBegoinAtZero = false;

      if(booleanPercentage){

        let amountsPercentage = [...amounts, ...marketIndices2];

        minValue = Math.min(...amountsPercentage);
        maxValue = Math.max(...amountsPercentage);
        minValueMarket = minValue;
        maxValueMarket = maxValue;
        booleanBegoinAtZero = true;

      }else{

         minValue = Math.min(...amounts);
         maxValue = Math.max(...amounts);
         minValueMarket = Math.min(...marketIndices);
         maxValueMarket = Math.max(...marketIndices);
         booleanBegoinAtZero = false;

      }



      this.chart = new Chart(this.myChartCanvas.nativeElement, {
        type: 'line',
        data: {
          labels: dates,
          datasets: [
            {
              label: 'Amount over Time',
              data: amounts,
              borderColor: 'rgb(75, 192, 192)',
              tension: 0.1,
              yAxisID: 'y',
             // hidden: this.curb1Display.value ?? false
            },
            {
              label: 'Indes over Time',
              data: marketIndices2,
              borderColor: 'rgb(211, 95, 17)',
              tension: 0.1,
              yAxisID: 'yMarket',
             // hidden: this.curb2Display.value ?? false
            }
          ],
        },
        options: {
          responsive: true, // Enable responsiveness
          scales: {
            x: {
              type: 'time',
              time: {
                unit: 'month',
              },
            },
            y: {
              position: 'left',
              beginAtZero: booleanBegoinAtZero,
              min: minValue,
              max: maxValue
            },
            yMarket: {
              position: 'right',
              beginAtZero: booleanBegoinAtZero,
              min: minValueMarket,
              max: maxValueMarket,
            },
          },
        },
      });
    }

    onFileSelected(event: any) {
      this.selectedFiles = Array.from(event.target.files);
    }


    uploadFiles() {

      const f64: FileBase64[] = [];

      const promises = this.selectedFiles.map((file) => {
        return new Promise<{ base64: string }>((resolve, reject) => {
          const reader = new FileReader();
          reader.onload = (e: any) => {
            const base64 = e.target.result.split(',')[1]; // Extract base64 part
            resolve({ base64: base64 });
          };
          reader.onerror = reject;
          reader.readAsDataURL(file);
        });
      });

      Promise.all(promises)
        .then((results) => {
          this.accountService.loadFiles(results).subscribe({
            next: (response) => {
              this.snackBar.open('Data loaded successfully!', 'Close', {
                duration: 3000,
                horizontalPosition: 'center',
                verticalPosition: 'bottom',
                panelClass: ['success-snackbar'],
              });
            },
            error: (error) => {
              console.error('Error from accountService:', error);
              let errorMessage = 'Error loading data. Please try again.';
              if (error && error.message) {
                errorMessage = `Error: ${error.message}`; // More specific error
              }
              this.snackBar.open(errorMessage, 'Close', {
                duration: 5000,
                horizontalPosition: 'center',
                verticalPosition: 'bottom',
                panelClass: ['error-snackbar'],
              });
            },
          });
        })
        .catch((promiseError) => {
          console.error('Error in Promise.all:', promiseError);
          this.snackBar.open('An unexpected error occurred.', 'Close', {
            duration: 5000,
            horizontalPosition: 'center',
            verticalPosition: 'bottom',
            panelClass: ['error-snackbar'],
          });
        });

    }


    saveState(): void {
      const stateToSave = {
        isSubmitted: this.isSubmitted,
        formData: this.myForm.value,
        };
      localStorage.setItem('formState', JSON.stringify(stateToSave));
    }

    onSubmit() {
      if (this.myForm.valid) {


        this.isSubmitted = true;


         // Sauvegarder l'état après la soumission
        this.saveState();

        const selectTypes = this.typeAccounts.value.filter((v: { checked: any; })=> v.checked);


        const selectType: string = selectTypes[0].name;


        console.log("**************************" + this.myForm.value.end);

        console.log("**************************" + this.myForm.value.endDate);

        console.log("**************************" + selectType);

        let dataAccountObservable: Observable<AccountDataResult>;
        let dataMarketObservable: Observable<MarketDataResult>;

        let booleanPercentage = this.myForm.value.percentageDisplay;

        if(selectType == '*All'){
          if(booleanPercentage){
            dataAccountObservable = this.accountService.getPercentageDate(this.myForm.value.end, this.myForm.value.endDate);
            dataMarketObservable = this.accountService.getMarketPercentage('^GSPC', this.myForm.value.end, this.myForm.value.endDate);
          }else{
            dataAccountObservable = this.accountService.getSumDate(this.myForm.value.end, this.myForm.value.endDate);
            dataMarketObservable = this.accountService.getMarket('^GSPC', this.myForm.value.end, this.myForm.value.endDate);
          }
        }else{
          if(booleanPercentage){
            dataAccountObservable = this.accountService.getSumPercentageType(selectType, this.myForm.value.end, this.myForm.value.endDate);
            dataMarketObservable = this.accountService.getMarketPercentage('^GSPC', this.myForm.value.end, this.myForm.value.endDate);
          }else{
            dataAccountObservable = this.accountService.getSumDateType(selectType, this.myForm.value.end, this.myForm.value.endDate);
            dataMarketObservable = this.accountService.getMarket('^GSPC', this.myForm.value.end, this.myForm.value.endDate);
          }
        }

        forkJoin({

            dataAccount: dataAccountObservable,
            dataMarket: dataMarketObservable

        }).subscribe(

          {
            next: (results) => {
              // 'results' sera un objet de la forme :
              // { dataApi1: ResultatDeLAPI1, dataApi2: ResultatDeLAPI2 }
              console.log('Résultats des deux APIs:', results);
              // Ici, tu peux manipuler les données combinées et continuer ton traitement

              this.accountValues = [];

              this.marketValues = [];

              if(booleanPercentage){
                this.sumDates = results.dataAccount as AccountDatePercentage[];
                this.sumDates.forEach(item => {

                  this.accountValues.push({
                    year: item.year,
                    month: item.month,
                    value: item.percentage
                  });

                });
              }else{
                this.sumDates = results.dataAccount as AccountDateSum[];
                this.sumDates.forEach(item => {

                  this.accountValues.push({
                    year: item.year,
                    month: item.month,
                    value: item.sum
                  });

                });
              }

              if(booleanPercentage){

                    this.marketIndices = results.dataMarket as MarketPercentage[];

                    this.marketIndices.forEach(item => {

                    this.marketValues.push({
                      year: item.year,
                      month: item.month,
                      value: item.percentage
                    });

                  });


              }else{

                    this.marketIndices = results.dataMarket as Market[];

                    this.marketIndices.forEach(item => {

                    this.marketValues.push({
                      year: item.year,
                      month: item.month,
                      value: item.indicePoint
                    });

                  });


              }



              if (this.chart) {
                this.chart.destroy();
              }

              this.createChart(this.accountValues, this.marketValues, booleanPercentage);
              this.updateDisplayCurve(0, this.curb1Display.value ?? false);
              this.updateDisplayCurve(1, this.curb2Display.value ?? false);


            },
            error: (error) => {
              console.error('Erreur lors de l\'appel à l\'une des APIs:', error);
              // Gestion des erreurs si l'un des appels échoue
            },
            complete: () => {
              console.log('Les deux appels d\'API sont terminés.');
              // Actions à effectuer une fois que tout est terminé (optionnel)
            }
          }

        );

      }
    }


    onCheckboxChange(name: string) {

  /*    console.log("box : " + name)

      this.typeAccounts.value.forEach((element: { name: string; checked: boolean; }) => {


        console.log("boxlec : " + element)

        if(element.name != name){
          console.log("boxlec2 : " + element);
          (element as unknown as FormGroup).checked = false;
        }

      });*/



          this.typeAccounts.controls.forEach(c => {


            if(c.get('name')?.value !=name){

              c.get('checked')?.setValue(false);

            }


          });



      }

      getMarketIndiceWithSum(sumDate: AccountDateSum[], indiceDate: Market[]){


      }

        /**
   * Fonction pour afficher ou masquer une courbe.
   * @param curveIndex L'index de la courbe (0 pour la première, 1 pour la deuxième).
   * @param isVisible L'état de la case à cocher (true pour visible, false pour masqué).
   */
    toggleCurve(curveIndex: number, event: Event): void {

      if(event && event.target){

        if (this.chart && this.chart.data.datasets[curveIndex]) {

            // (event.target as HTMLInputElement) : C'est ce qu'on appelle un "type casting".
            // Par défaut, event.target est de type EventTarget. Pour accéder à des propriétés
            // spécifiques comme checked, on doit dire à TypeScript que l'élément est en fait
            // un HTMLInputElement.
            // Cela permet au compilateur de savoir que la propriété .checked est bien disponible et de corriger l'erreur.

            const isChecked = (event.target as HTMLInputElement).checked;
            // Met à jour la propriété 'hidden' de la courbe
            this.updateDisplayCurve(curveIndex, isChecked);

        }
      }
    }

    updateDisplayCurve(curveIndex: number, isDisplay: boolean): void {

            // Met à jour la propriété 'hidden' de la courbe
            (this.chart.data.datasets[curveIndex] as ChartDataset<'line'>).hidden = !isDisplay;

            // Met à jour le graphique pour appliquer les changements
            this.chart.update();

    }

    togglePercentage($event: Event) {

       if(event && event.target){

          if (this.chart){

              const booleanPercentage = (event.target as HTMLInputElement).checked;


          }


       }

    }

}
