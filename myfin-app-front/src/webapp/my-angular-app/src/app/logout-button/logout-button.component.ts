import { Component } from '@angular/core';
import { Router } from '@angular/router'; // Nécessaire pour la redirection
import { AuthService } from '../auth-service/auth.service';
import { inject, Injectable } from '@angular/core';

@Component({
  selector: 'app-logout-button',
  templateUrl: './logout-button.component.html',
  styleUrls: ['./logout-button.component.css']
})

@Injectable({
  providedIn: 'root' // <-- C'est ici qu'il faut s'assurer que c'est bien configuré
})

export class LogoutButtonComponent {

  authService = inject(AuthService); // Injecte le service directement

  constructor(private router: Router) { }

  logout(): void {
    this.authService.logout()
  }
}
