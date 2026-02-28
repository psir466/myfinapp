import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

export interface AppError {
  status: number;
  message: string;
  originalError?: any;
  timestamp: Date;
}

@Injectable({
  providedIn: 'root'
})
export class ErrorService {
  private errorSubject = new Subject<AppError>();
  public error$ = this.errorSubject.asObservable();

  private errorHistory: AppError[] = [];

  /**
   * Signale une erreur qui sera loggée et potentiellement affichée
   * @param error L'erreur à signaler
   */
  reportError(error: AppError): void {
    this.errorSubject.next(error);
    this.errorHistory.push(error);

    // Garder seulement les 50 dernières erreurs en mémoire
    if (this.errorHistory.length > 50) {
      this.errorHistory.shift();
    }
  }

  /**
   * Retourne l'historique des erreurs
   */
  getErrorHistory(): AppError[] {
    return [...this.errorHistory];
  }

  /**
   * Efface l'historique des erreurs
   */
  clearErrorHistory(): void {
    this.errorHistory = [];
  }

  /**
   * Obtient les erreurs des dernières N secondes
   * @param seconds Nombre de secondes
   */
  getRecentErrors(seconds: number): AppError[] {
    const now = new Date().getTime();
    return this.errorHistory.filter(error => {
      const errorTime = error.timestamp.getTime();
      return (now - errorTime) < (seconds * 1000);
    });
  }

  /**
   * Compte les erreurs d'un type spécifique
   * @param statusCode Le code de statut HTTP à filtrer
   */
  countErrorsByStatus(statusCode: number): number {
    return this.errorHistory.filter(error => error.status === statusCode).length;
  }
}
