// src/app/auth/login/login.component.ts
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth-service/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms'; // <-- Ajoutez cette ligne

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true, // <-- Assurez-vous que votre composant est autonome
  imports: [
    FormsModule // <-- Ajoutez FormsModule ici !
    // Si vous avez d'autres modules ou composants importés ici,
    // ajoutez une virgule et ajoutez FormsModule.
    // Par exemple: CommonModule, RouterLink, FormsModule
  ]
})
export class LoginComponent implements OnInit {
  username!: string;
  password!: string;
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {


    console.log('LoginComponent initialized !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!');


    if (this.authService.getToken()) {
      this.router.navigate(['/accountlist']); // Rediriger si déjà connecté
    }
  }

  onSubmit(): void {
    this.authService.login({ usernameOrEmail: this.username, password: this.password }).subscribe({
      next: (response) => {
        // La redirection est gérée dans le service
      },
      error: (err) => {
        this.errorMessage = 'Nom d\'utilisateur ou mot de passe incorrect.';
        console.error('Login error', err);
      }
    });
  }
}
