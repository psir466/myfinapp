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

    return this.http.post<string>(`http://localhost:8100/backfront/uploadBase64Files`, files64);

  }




}
