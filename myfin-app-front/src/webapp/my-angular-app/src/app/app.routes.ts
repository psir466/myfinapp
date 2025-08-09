import { Routes } from '@angular/router';
import { AccountListComponent } from './account-list/account-list.component'
import { LoginComponent } from './login/login.component'
import { Guard } from './guard/guard'

export const routes: Routes = [

  { path: 'login', component: LoginComponent },
  {path: 'accountlist', component: AccountListComponent, canActivate: [Guard] },
  { path: '', redirectTo: '/accountlist', pathMatch: 'full' },
  { path: '**', redirectTo: '/accountlist' } // Redirection pour les routes inconnues


];
