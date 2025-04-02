import { Component, OnInit, ElementRef, ViewChild, inject } from '@angular/core';
import { AccountServiceService } from '../account-service/account-service.service';
import { Account, AccountLine, AmountOfMoney, AccountDateSum, FileBase64 } from '../model/account-model';
import { formatDate } from '@angular/common';
import { animate } from '@angular/animations';
import { Chart, registerables} from 'chart.js';
import 'chartjs-adapter-moment';
import { HttpClient } from '@angular/common/http';
import { FormArray, ReactiveFormsModule, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';



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
  @ViewChild('myChart') myChartCanvas!: ElementRef;
  chart: any;

  startDate = new FormControl('');
  end = new FormControl('');
  typeAccount = new FormControl('');
  endDate = new FormControl('');
  //private fb = inject(FormBuilder);
  myForm!: FormGroup;

	constructor(private accountService: AccountServiceService, private http:  HttpClient, private fb: FormBuilder){}

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

         });


         this.accountTypes.map((t) => {

          let at = this.fb.group({ name: `${t}`, checked: false })

          this.typeAccounts.push(at);

        });



         console.log(this.myForm);


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


    createChart(data: AccountDateSum[]): void {


      const dates = data.map((d) => {

        let dateStr:string = `${d.year}-${d.month}-01`;

        console.log(dateStr);

        return new Date(dateStr)});


      const amounts = data.map((d) => d.sum);

      let minValue = Math.min(...amounts);




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
            },
          ],
        },
        options: {
          scales: {
            x: {
              type: 'time',
              time: {
                unit: 'month',
              },
            },
            y: {
              beginAtZero: false,
              min: minValue
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

      Promise.all(promises).then((results) => {
        this.accountService.loadFiles(results).subscribe(
          (response) => {
            console.log('Upload successful', response);
          },
          (error) => {
            console.error('Upload failed', error);
          }
        );
      });

    }


    onSubmit() {
      if (this.myForm.valid) {





        const selectTypes = this.typeAccounts.value.filter((v: { checked: any; })=> v.checked);


        const selectType: string = selectTypes[0].name;


        console.log("**************************" + this.myForm.value.end);

        console.log("**************************" + this.myForm.value.endDate);

        console.log("**************************" + selectType);

        this.accountService.getSumDateType(selectType, this.myForm.value.end, this.myForm.value.endDate).subscribe((data) => {

          console.log(data);

          this.sumDates = data;

          if (this.chart) {
            this.chart.destroy();
          }

          this.createChart(this.sumDates);

          console.log(this.accounts);

        });
      }
    }




}
