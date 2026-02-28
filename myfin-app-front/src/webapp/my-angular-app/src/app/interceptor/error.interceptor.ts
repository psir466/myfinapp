import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError, retry, timer } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '../auth-service/auth.service';

export const errorInterceptor: HttpInterceptorFn = (request, next) => {
  const snackBar = inject(MatSnackBar);
  const router = inject(Router);
  const authService = inject(AuthService);

  return next(request).pipe(
    // Retry automatique pour les erreurs réseau (max 2 fois)
    retry({
      count: 2,
      delay: (error, retryCount) => {
        // Retry seulement si c'est une erreur réseau ou 5xx
        if (error instanceof HttpErrorResponse) {
          // Ne pas retry pour les erreurs 4xx (sauf timeout)
          if (error.status >= 400 && error.status < 500) {
            // Abort retry by throwing the error
            throw error;
          }
          // Retry avec délai exponentiel pour les erreurs serveur
          return timer(Math.pow(2, retryCount) * 1000);
        }
        return timer(Math.pow(2, retryCount) * 1000);
      }
    }),
    catchError((error: HttpErrorResponse) => {
      let errorMessage = 'Une erreur est survenue';
      let severityClass = 'error-snackbar';

      // Traiter selon le code d'erreur
      switch (error.status) {
        case 0:
          // Erreur réseau
          errorMessage = 'Erreur de connexion. Vérifiez votre internet.';
          break;

        case 400:
          // Bad Request
          errorMessage = error.error?.message || 'Données invalides. Veuillez vérifier votre saisie.';
          break;

        case 401:
          // Non authentifié
          errorMessage = 'Session expirée. Veuillez vous reconnecter.';
          authService.logout();
          router.navigate(['/login']);
          break;

        case 403:
          // Accès refusé
          errorMessage = 'Vous n\'avez pas les permissions pour cette action.';
          severityClass = 'warning-snackbar';
          break;

        case 404:
          // Non trouvé
          errorMessage = 'La ressource demandée n\'existe pas.';
          break;

        case 500:
        case 502:
        case 503:
          // Erreurs serveur
          errorMessage = 'Erreur serveur. Veuillez réessayer plus tard.';
          break;

        case 504:
          // Gateway timeout
          errorMessage = 'Timeout serveur. La requête a pris trop de temps.';
          break;

        default:
          errorMessage = error.error?.message || errorMessage;
      }

      // Afficher le message d'erreur à l'utilisateur
      snackBar.open(errorMessage, 'Fermer', {
        duration: 5000,
        horizontalPosition: 'center',
        verticalPosition: 'bottom',
        panelClass: [severityClass]
      });

      // Logger l'erreur pour le debugging avec format amélioré
      const timestamp = new Date().toISOString();
      const errorDetails = {
        timestamp,
        status: error.status,
        statusText: error.statusText,
        message: errorMessage,
        url: error.url,
        method: request.method,
        errorBody: error.error
      };

      console.group(`❌ [${error.status}] ${error.statusText || 'Error'}`);
      console.log('%cTimestamp:', 'color: gray', timestamp);
      console.log('%cMethod:', 'color: blue', request.method);
      console.log('%cURL:', 'color: blue', error.url);
      console.log('%cMessage:', 'color: red', errorMessage);
      console.log('%cDetails:', 'color: orange', error.error);
      console.groupEnd();

      // Log pour les outils d'analytics
      if ((window as any).__errorLog) {
        (window as any).__errorLog.push(errorDetails);
      }

      // Retourner l'erreur au composant qui peut encore la traiter
      return throwError(() => ({
        status: error.status,
        message: errorMessage,
        originalError: error
      }));
    })
  );
};
