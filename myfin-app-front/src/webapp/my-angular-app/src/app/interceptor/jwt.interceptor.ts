// auth.interceptor.ts
import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../auth-service/auth.service';
import { catchError, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (request, next) => {
  const authService = inject(AuthService); // Injecte le service directement
  const token = authService.getToken();
  console.log('on est dans interceptor token ' + token)
  if (token) {
    const authRequest = request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(authRequest).pipe(
      catchError(error => {
        console.error('Error in authInterceptor:', error);
        return throwError(() => error);
      })
    );
  }

  return next(request).pipe(
    catchError(error => {
      console.error('Error in authInterceptor:', error);
      return throwError(() => error);
    })
  );
};
