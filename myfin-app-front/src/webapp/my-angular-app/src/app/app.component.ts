import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AccountListComponent } from './account-list/account-list.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, AccountListComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'my-angular-app';
}
