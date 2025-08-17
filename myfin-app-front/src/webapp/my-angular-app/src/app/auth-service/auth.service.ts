// src/app/auth/auth.service.ts
import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8100/backfront'; // URL de votre BoF
  private loggedIn = new BehaviorSubject<boolean>(this.hasToken());


  constructor(private http: HttpClient, private router: Router, private jwtHelper: JwtHelperService) { }

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


   // Décoder le token et récupérer les informations
  public getDecodedToken(): any {
    const token = this.getToken();
    if (token) {
      return this.jwtHelper.decodeToken(token);
    }
    return null;
  }

  public getUserRoles(): string[] | null {
    const decodedToken = this.getDecodedToken();
    // Le nom du claim doit correspondre à celui de votre backend (ex: 'roles')

    console.log('******************************* decode token ********************************');

    console.log(decodedToken);

    return decodedToken && decodedToken.roles ? decodedToken.roles : null;
  }

// Vérifier si l'utilisateur possède un rôle spécifique
  public hasRole(roleToCheck: string): boolean {
    const userRoles = this.getUserRoles();

    console.log('############################roles du user ####################: ' + userRoles);

    if (userRoles) {
      return userRoles.includes(roleToCheck);
    }
    return false;
  }

  // Vérifier si l'utilisateur est authentifié
  public isAuthenticated(): boolean {
    const token = this.getToken();
    return !this.jwtHelper.isTokenExpired(token);
  }

}
