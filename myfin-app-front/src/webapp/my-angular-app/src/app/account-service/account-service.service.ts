import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Account, AccountLine, AmountOfMoney, AccountDateSum, FileBase64 } from '../model/account-model';

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


  loadFiles(files64 :FileBase64[]): Observable<string> {

    return this.http.post(`http://localhost:8100/backfront/uploadBase64Files`, files64, {responseType: 'text'});

  }


  getAccountType(): Observable<string[]> {

    return this.http.get<string[]>(`http://localhost:8100/backfront/getAccountTypes`);

  }


  getSumDateType(typeAccount :string | null | undefined , start :string | null | undefined, end :string | null | undefined): Observable<AccountDateSum[]> {
    return this.http.get<AccountDateSum[]>(`http://localhost:8100/backfront/sumdatetype/${typeAccount}/${start}/${end}`);

  }



}
