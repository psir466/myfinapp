import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter, withHashLocation } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { routes } from './app.routes';
import { importProvidersFrom } from '@angular/core';
import { authInterceptor } from './interceptor/jwt.interceptor';
import { AuthService } from './auth-service/auth.service';
import { JwtModule } from '@auth0/angular-jwt'; // Import the module


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
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes, withHashLocation()),
    provideHttpClient(withInterceptors([authInterceptor]))
  ]
};
