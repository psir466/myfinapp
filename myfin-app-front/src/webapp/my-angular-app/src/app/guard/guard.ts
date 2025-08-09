// src/app/auth/auth.guard.ts (avant Angular 15, avec un NgModule)
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../auth-service/auth.service'; // Votre service d'authentification

@Injectable({
  providedIn: 'root' // Le rend injectable à la racine de l'application
})
export class Guard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if (this.authService.getToken()) { // Ou this.authService.isLoggedIn.value pour une BehaviorSubject
      // L'utilisateur est connecté, vérifie si des rôles sont nécessaires
      // const requiredRoles = route.data['roles'] as string[]; // Exemple si vous passez des rôles dans les données de la route

      // Si vous avez de la logique d'autorisation basée sur les rôles:
      // if (requiredRoles && !this.authService.hasAnyRole(requiredRoles)) {
      //   return this.router.createUrlTree(['/forbidden']); // Page d'accès refusé
      // }

      return true; // Autoriser l'accès
    } else {
      // L'utilisateur n'est pas connecté, redirige vers la page de connexion
      console.log('User not authenticated, redirecting to login...');
      return this.router.createUrlTree(['/login']); // Redirige vers la page de connexion
    }
  }
}
