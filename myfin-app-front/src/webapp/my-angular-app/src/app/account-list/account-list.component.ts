import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { AccountServiceService } from '../account-service/account-service.service';
import { Account, AccountLine, AmountOfMoney, AccountDateSum } from '../model/account-model';
import { formatDate } from '@angular/common';
import { animate } from '@angular/animations';
import { Chart, registerables} from 'chart.js';
import 'chartjs-adapter-moment';



Chart.register(...registerables);


@Component({
  selector: 'app-account-list',
  imports: [],
  templateUrl: './account-list.component.html',
  styleUrl: './account-list.component.css'
})


export class AccountListComponent implements OnInit{

	accounts: Account[] = [];
  sumDates: AccountDateSum[] = [];
  @ViewChild('myChart') myChartCanvas!: ElementRef;
  chart: any;

	constructor(private accountService: AccountServiceService){}

		 ngOnInit(): void {

				console.log('on est là');

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

			    });

	}


    createChart(data: AccountDateSum[]): void {


      const dates = data.map((d) => {

        let dateStr:string = `${d.year}-${d.month}-01`;

        console.log(dateStr);

        return new Date(dateStr)});


      const amounts = data.map((d) => d.sum);

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
              beginAtZero: true,
            },
          },
        },
      });
    }


}
