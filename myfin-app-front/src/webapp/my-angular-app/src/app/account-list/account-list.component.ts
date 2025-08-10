import { Component, OnInit, ElementRef, ViewChild, inject } from '@angular/core';
import { AccountServiceService } from '../account-service/account-service.service';
import { Account, AccountLine, AmountOfMoney, AccountDateSum, FileBase64, Market } from '../model/account-model';
import { formatDate } from '@angular/common';
import { animate } from '@angular/animations';
import { Chart, registerables, ChartDataset, ChartOptions } from 'chart.js';
import 'chartjs-adapter-moment';
import { HttpClient } from '@angular/common/http';
import { FormArray, ReactiveFormsModule, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { forkJoin } from 'rxjs';


Chart.register(...registerables);


@Component({
  selector: 'app-account-list',
  imports: [ReactiveFormsModule],
  templateUrl: './account-list.component.html',
  styleUrl: './account-list.component.css'
})


export class AccountListComponent implements OnInit{

  selectedFiles: File[] = [];
	accounts: Account[] = [];
  accountTypes: string[] = [];
  sumDates: AccountDateSum[] = [];
  marketIndices: Market[] = [];
  @ViewChild('myChart') myChartCanvas!: ElementRef;
  chart: any;
  isSubmitted = false; // Nouvelle variable pour suivre l'état

  // problème avec startdate systemantiqument non remplie bien que saisie !!!!!!!!!!!!!!!!!!!????????????????????????????????
  startDate = new FormControl('2020-01-01');
  end = new FormControl('2020-01-01');
  typeAccount = new FormControl('');
  endDate = new FormControl('2030-01-01');
  //private fb = inject(FormBuilder);
  myForm!: FormGroup;
  curb1Display = new FormControl(true);
  curb2Display = new FormControl(true);

	constructor(private accountService: AccountServiceService, private http:  HttpClient, private fb: FormBuilder, private snackBar: MatSnackBar){}

		 ngOnInit(): void {



      this.accountService.getAccountType().subscribe((data) => {

        this.accountTypes = data;

        this.accountTypes.push('*All');


        console.log(this.accountTypes);

        this.myForm = this.fb.group({

          startDate: this.startDate,
          end: this.end,
          endDate: this.endDate,
          typeAccounts:  this.fb.array([]),
          curb1Display: this.curb1Display,
          curb2Display: this.curb2Display

         });


         this.accountTypes.map((t) => {

          let at = this.fb.group({ name: `${t}`, checked: false });

          this.typeAccounts.push(at);

        });



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


    createChart(data: AccountDateSum[], dataMarket: Market[]): void {


      const dates = data.map((d) => {

        let dateStr:string = `${d.year}-${d.month}-01`;

        console.log(dateStr);

        return new Date(dateStr)
      });


      const amounts = data.map((d) => d.sum);
      const marketIndices = dataMarket.map((m) => m.indicePoint);

      let minValue = Math.min(...amounts);
      let minValueMarket = Math.min(...marketIndices);



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
              data: marketIndices,
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
              beginAtZero: false,
              min: minValue
            },
            yMarket: {
              position: 'right',
              beginAtZero: false,
              min: minValueMarket
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


        if(selectType == '*All'){



          forkJoin({

              dataAccount: this.accountService.getSumDate(this.myForm.value.end, this.myForm.value.endDate),
              dataMarket: this.accountService.getMarket('^GSPC', this.myForm.value.end, this.myForm.value.endDate)

          }).subscribe(

            {
              next: (results) => {
                // 'results' sera un objet de la forme :
                // { dataApi1: ResultatDeLAPI1, dataApi2: ResultatDeLAPI2 }
                console.log('Résultats des deux APIs:', results);
                // Ici, tu peux manipuler les données combinées et continuer ton traitement


                this.sumDates = results.dataAccount;
                this.marketIndices = results.dataMarket;



                if (this.chart) {
                  this.chart.destroy();
                }

                this.createChart(this.sumDates, this.marketIndices);
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


          /*  this.accountService.getSumDate(this.myForm.value.end, this.myForm.value.endDate).subscribe((data) => {

              console.log(data);

              this.sumDates = data;

              if (this.chart) {
                this.chart.destroy();
              }

              this.createChart(this.sumDates);

              console.log(this.accounts);

          });*/

        }else{


          forkJoin({

            dataAccount: this.accountService.getSumDateType(selectType, this.myForm.value.end, this.myForm.value.endDate),
            dataMarket: this.accountService.getMarket('^GSPC', this.myForm.value.end, this.myForm.value.endDate)

        }).subscribe(

          {
            next: (results) => {
              // 'results' sera un objet de la forme :
              // { dataApi1: ResultatDeLAPI1, dataApi2: ResultatDeLAPI2 }
              console.log('Résultats des deux APIs:', results);
              // Ici, tu peux manipuler les données combinées et continuer ton traitement


              this.sumDates = results.dataAccount;
              this.marketIndices = results.dataMarket;

              if (this.chart) {
                this.chart.destroy();
              }

              this.createChart(this.sumDates, this.marketIndices);
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



           /* this.accountService.getSumDateType(selectType, this.myForm.value.end, this.myForm.value.endDate).subscribe((data) => {

              console.log(data);

              this.sumDates = data;

              if (this.chart) {
                this.chart.destroy();
              }

              this.createChart(this.sumDates);

              console.log(this.accounts);

          });*/



        }
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

}
