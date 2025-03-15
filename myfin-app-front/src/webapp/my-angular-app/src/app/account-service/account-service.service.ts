import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Account, AccountLine, AmountOfMoney, AccountDateSum } from '../model/account-model';

@Injectable({
  providedIn: 'root'
})
export class AccountServiceService {

  constructor(private http: HttpClient) { }

  getAccounts(): Observable<Account[]> {
	    return this.http.get<Account[]>(`http://localhost:8100/backfront/accounts`);


  }


  getSumDate(start :string, end: string): Observable<AccountDateSum[]> {
    return this.http.get<AccountDateSum[]>(`http://localhost:8100/backfront/sumdate/${start}/${end}`);

  }




}
