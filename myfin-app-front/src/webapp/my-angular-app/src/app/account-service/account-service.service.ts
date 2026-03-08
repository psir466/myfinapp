import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Account, AccountLine, AmountOfMoney, AccountDateSum, FileBase64, Market, AccountDatePercentage, MarketPercentage } from '../model/account-model';

@Injectable({
  providedIn: 'root'
})
export class AccountServiceService {

  private baseUrl = 'http://finapp.local/backfront'; // URL de votre BoF

  constructor(private http: HttpClient) { }

  getAccounts(): Observable<Account[]> {
	    return this.http.get<Account[]>(`${this.baseUrl}/accounts`);


  }


  getSumDate(start :string, end: string): Observable<AccountDateSum[]> {
    return this.http.get<AccountDateSum[]>(`${this.baseUrl}/sumdate/${start}/${end}`);

  }

    getPercentageDate(start :string, end: string): Observable<AccountDatePercentage[]> {
    return this.http.get<AccountDatePercentage[]>(`${this.baseUrl}/percentagedate/${start}/${end}`);

  }


  loadFiles(files64 :FileBase64[]): Observable<string> {

    return this.http.post(`${this.baseUrl}/uploadBase64Files`, files64, {responseType: 'text'});

  }


  getAccountType(): Observable<string[]> {

    return this.http.get<string[]>(`${this.baseUrl}/getAccountTypes`);

  }


  getSumDateType(typeAccount :string | null | undefined , start :string | null | undefined, end :string | null | undefined): Observable<AccountDateSum[]> {
    return this.http.get<AccountDateSum[]>(`${this.baseUrl}/sumdatetype/${typeAccount}/${start}/${end}`);

  }

    getSumPercentageType(typeAccount :string | null | undefined , start :string | null | undefined, end :string | null | undefined): Observable<AccountDatePercentage[]> {
    return this.http.get<AccountDatePercentage[]>(`${this.baseUrl}/percentagedatetype/${typeAccount}/${start}/${end}`);

  }

  getMarket(code :string | null | undefined , start :string | null | undefined, end :string | null | undefined): Observable<Market[]> {
    return this.http.get<Market[]>(`${this.baseUrl}/markets/${code}/${start}/${end}`);

  }


    getMarketPercentage(code :string | null | undefined , start :string | null | undefined, end :string | null | undefined): Observable<MarketPercentage[]> {
    return this.http.get<MarketPercentage[]>(`${this.baseUrl}/marketsPercentage/${code}/${start}/${end}`);

  }



}
