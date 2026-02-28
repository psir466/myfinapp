import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter, withHashLocation } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { routes } from './app.routes';
import { importProvidersFrom } from '@angular/core';
import { authInterceptor } from './interceptor/jwt.interceptor';
import { errorInterceptor } from './interceptor/error.interceptor';
import { AuthService } from './auth-service/auth.service';
import { JwtModule } from '@auth0/angular-jwt'; // Import the module
import { provideAnimations } from '@angular/platform-browser/animations'; // Pour Material


// Fonction pour obtenir le token JWT
export function tokenGetter() {
  return localStorage.getItem('jwt_token');
}


export const appConfig: ApplicationConfig = {
  providers: [
   importProvidersFrom( // <-- Use this function to import the module
      JwtModule.forRoot({
        config: {
          tokenGetter: tokenGetter,
        }
      })
    ),
    provideAnimations(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes, withHashLocation()),
    // L'ordre est important : JWT d'abord, puis gestion des erreurs
    provideHttpClient(withInterceptors([authInterceptor, errorInterceptor]))
  ]
};
