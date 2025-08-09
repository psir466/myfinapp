// src/app/auth/auth.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8100/backfront'; // URL de votre BoF
  private loggedIn = new BehaviorSubject<boolean>(this.hasToken());

  constructor(private http: HttpClient, private router: Router) { }

  private hasToken(): boolean {
    return !!localStorage.getItem('jwt_token');
  }

  get isLoggedIn(): Observable<boolean> {
    return this.loggedIn.asObservable();
  }

  login(credentials: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, credentials).pipe(
      tap((response: any) => {
        if (response.accessToken) {
          localStorage.setItem('jwt_token', response.accessToken);
          this.loggedIn.next(true);
          // Rediriger l'utilisateur après connexion réussie
          this.router.navigate(['/dashboard']); // Ou toute autre page protégée
        }
      }),
      catchError(error => {
        console.error('Login failed:', error);
        // Gérer l'erreur (afficher un message à l'utilisateur)
        throw error;
      })
    );
  }

  logout(): void {
    localStorage.removeItem('jwt_token');
    this.loggedIn.next(false);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem('jwt_token');
  }
}
