# Architecture de la Gestion des Erreurs - Détails Techniques

## 🏗️ Architecture globale

```
┌─────────────────────────────────────────────────────────────────────┐
│                     APPLICATION ANGULAR                              │
├─────────────────────────────────────────────────────────────────────┤
│                                                                       │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │ COMPOSANTS (account-list, header, etc.)                     │   │
│  │ - Appellent AccountServiceService                           │   │
│  │ - Souscrivent aux Observables                               │   │
│  │ - Affichent les données                                     │   │
│  └──────────────┬──────────────────────────────────────────────┘   │
│                 │                                                    │
│                 ▼                                                    │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │ SERVICES (AccountServiceService)                            │   │
│  │ - Construisent les requêtes HTTP                            │   │
│  │ - Retournent des Observables                                │   │
│  └──────────────┬──────────────────────────────────────────────┘   │
│                 │                                                    │
│                 ▼                                                    │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │ HTTP CLIENT (Angular HttpClient)                            │   │
│  └──────────────┬──────────────────────────────────────────────┘   │
│                 │                                                    │
│                 ▼ (Requête HTTP)                                    │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │ INTERCEPTORS PIPELINE                                       │   │
│  │ ┌──────────────────────────────────────────────────────┐   │   │
│  │ │ 1️⃣  JWT INTERCEPTOR                                 │   │   │
│  │ │    - Clone la requête                                │   │   │
│  │ │    - Ajoute le Bearer token                          │   │   │
│  │ │    - Passe au prochain interceptor                   │   │   │
│  │ └──────────────────────────────────────────────────────┘   │   │
│  │                 │                                              │   │
│  │                 ▼                                              │   │
│  │ ┌──────────────────────────────────────────────────────┐   │   │
│  │ │ 2️⃣  ERROR INTERCEPTOR                               │   │   │
│  │ │    - Appel du backend                                │   │   │
│  │ │    - Détecte les erreurs                             │   │   │
│  │ │    - Gère les retries                                │   │   │
│  │ │    - Affiche les snackbars                           │   │   │
│  │ │    - Gère les redirections (401)                     │   │   │
│  │ └──────────────────────────────────────────────────────┘   │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                 │                                                    │
│                 ▼ (Réponse HTTP)                                    │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │ OBSERVABLE DANS LE COMPOSANT                                │   │
│  │ - next: Données reçues                                      │   │
│  │ - error: Erreur gérée par l'intercepteur                    │   │
│  │ - complete: Fin de la requête                               │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                                                                       │
└─────────────────────────────────────────────────────────────────────┘
```

---

## 📊 Flux détaillé d'une requête avec gestion d'erreur

### Cas 1: Requête réussie (200 OK)

```
Composant appelle: this.accountService.getAccounts()
                           ↓
AccountService.getAccounts()
  return this.http.get<Account[]>(url)
                           ↓
HTTP Client crée la requête GET
                           ↓
JWT Interceptor
  ✓ Ajoute Authorization: Bearer TOKEN
  ✓ Passe au prochain interceptor
                           ↓
Error Interceptor
  ✓ Appelle next(request)
  ✓ Pas d'erreur -> .pipe() continue
                           ↓
BACKEND retourne 200 OK + Données JSON
                           ↓
Observable reçoit les données
                           ↓
Composant .subscribe({
  next: (data) => { ... } ✓ EXECUTE
})
```

### Cas 2: Erreur 401 (Session expirée)

```
Composant appelle: this.accountService.getAccounts()
                           ↓
JWT Interceptor ajoute le token
                           ↓
BACKEND retourne 401 Unauthorized
                           ↓
Error Interceptor.catchError()
  ├─ case 401:
  │   ├─ errorMessage = "Session expirée. Veuillez vous reconnecter."
  │   ├─ authService.logout() ← Supprime le token
  │   ├─ router.navigate(['/login']) ← Redirection
  │   └─ snackBar.open(errorMessage) ← Affiche le message
  │
  ├─ Logging: console.error('[401] Unauthorized')
  │
  └─ return throwError() avec l'erreur
                           ↓
Observable reçoit l'erreur (mais l'utilisateur est déjà redirigé)
                           ↓
Composant .subscribe({
  error: (error) => { ... } ← Peut logger si besoin
})
```

### Cas 3: Erreur réseau (Status 0)

```
Composant appelle: this.accountService.getAccounts()
                           ↓
JWT Interceptor ajoute le token
                           ↓
REQUETE ECHOUE (Internet coupée)
  Status: 0, StatusText: ""
                           ↓
Error Interceptor.retry()
  ┌─────────────────────────────────┐
  │ Retry #1 après 1 seconde        │
  │ ┌─────────────────────────────┐ │
  │ │ Nouvelle tentative...       │ │
  │ │ ECHOUE ENCORE               │ │
  │ └─────────────────────────────┘ │
  └─────────────────────────────────┘
                           ↓
Error Interceptor.retry()
  ┌─────────────────────────────────┐
  │ Retry #2 après 2 secondes       │
  │ ┌─────────────────────────────┐ │
  │ │ Nouvelle tentative...       │ │
  │ │ ECHOUE ENCORE               │ │
  │ └─────────────────────────────┘ │
  └─────────────────────────────────┘
                           ↓
Error Interceptor.catchError()
  ├─ case 0:
  │   ├─ errorMessage = "Erreur de connexion. Vérifiez votre internet."
  │   └─ snackBar.open(errorMessage) ← Affiche le message
  │
  └─ return throwError()
                           ↓
Composant .subscribe({
  error: (error) => { console.log(error) }
})
```

### Cas 4: Erreur 500 (Serveur)

```
Composant appelle: this.accountService.getMarkets()
                           ↓
JWT Interceptor ajoute le token
                           ↓
BACKEND retourne 500 Internal Server Error
                           ↓
Error Interceptor.retry() {
  count: 2,
  delay: (error, retryCount) => timer(Math.pow(2, retryCount) * 1000)
}
  ┌────────────────────────────────────┐
  │ Retry #1 après 2^1 = 2 secondes   │
  │ ┌────────────────────────────────┐ │
  │ │ BACKEND retourne 500 AGAIN     │ │
  │ └────────────────────────────────┘ │
  └────────────────────────────────────┘
                           ↓
  ┌────────────────────────────────────┐
  │ Retry #2 après 2^2 = 4 secondes   │
  │ ┌────────────────────────────────┐ │
  │ │ BACKEND retourne 500 AGAIN     │ │
  │ └────────────────────────────────┘ │
  └────────────────────────────────────┘
                           ↓
Error Interceptor.catchError()
  ├─ case 500:
  │   ├─ errorMessage = "Erreur serveur. Veuillez réessayer plus tard."
  │   └─ snackBar.open(errorMessage) ← Affiche le message
  │
  └─ return throwError()
                           ↓
Composant .subscribe({
  error: (error) => { ... }
})
```

---

## 🔌 Détails des intercepteurs

### JWT Interceptor (jwt.interceptor.ts)

```typescript
export const authInterceptor: HttpInterceptorFn = (request, next) => {
  const authService = inject(AuthService);
  const token = authService.getToken();
  
  if (token) {
    // Clone la requête et ajoute le header
    const authRequest = request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(authRequest); // Passe au prochain interceptor
  }
  
  return next(request); // Passe sans modification si pas de token
};
```

### Error Interceptor (error.interceptor.ts)

```typescript
export const errorInterceptor: HttpInterceptorFn = (request, next) => {
  const snackBar = inject(MatSnackBar);
  const router = inject(Router);
  const authService = inject(AuthService);

  return next(request).pipe(
    // Retry avec délai exponentiel
    retry({
      count: 2,
      delay: (error, retryCount) => {
        if (error instanceof HttpErrorResponse) {
          if (error.status >= 400 && error.status < 500) {
            return throwError(() => error);
          }
          return timer(Math.pow(2, retryCount) * 1000); // 2s, 4s
        }
        return timer(Math.pow(2, retryCount) * 1000);
      }
    }),
    
    // Gestion centralisée des erreurs
    catchError((error: HttpErrorResponse) => {
      let errorMessage = 'Une erreur est survenue';
      
      switch (error.status) {
        case 0:
          errorMessage = 'Erreur de connexion...';
          break;
        case 401:
          authService.logout();
          router.navigate(['/login']);
          break;
        // ... autres cas ...
      }
      
      // Affiche le snackbar
      snackBar.open(errorMessage, 'Fermer', {
        duration: 5000,
        horizontalPosition: 'center',
        verticalPosition: 'bottom',
        panelClass: ['error-snackbar']
      });
      
      // Log pour le debugging
      console.error(`[${error.status}] ${error.message}:`, error.error);
      
      // Retourne l'erreur au composant
      return throwError(() => ({
        status: error.status,
        message: errorMessage,
        originalError: error
      }));
    })
  );
};
```

---

## 🎯 Mapping des codes HTTP

```javascript
{
  0: { message: 'Erreur de connexion', retry: true, severity: 'error' },
  400: { message: 'Données invalides', retry: false, severity: 'error' },
  401: { message: 'Session expirée', retry: false, severity: 'error', action: 'logout+redirect' },
  403: { message: 'Pas de permissions', retry: false, severity: 'warning' },
  404: { message: 'Ressource inexistante', retry: false, severity: 'error' },
  500: { message: 'Erreur serveur', retry: true, severity: 'error' },
  502: { message: 'Erreur serveur', retry: true, severity: 'error' },
  503: { message: 'Service indisponible', retry: true, severity: 'error' },
  504: { message: 'Timeout serveur', retry: true, severity: 'error' }
}
```

---

## 🧬 Service d'erreurs (error.service.ts)

```typescript
@Injectable({ providedIn: 'root' })
export class ErrorService {
  private errorSubject = new Subject<AppError>();
  public error$ = this.errorSubject.asObservable();
  private errorHistory: AppError[] = [];
  
  // Peut être utilisé pour:
  // 1. Dashboard d'erreurs
  // 2. Analytics (erreurs les plus fréquentes)
  // 3. Alertes (trop d'erreurs 500)
  // 4. Debugging avancé
}
```

---

## 📈 Délai de retry exponentiel

```
Tentative #1: Immédiate (succès?)
Tentative #2: 2^1 * 1000ms = 2 secondes (succès?)
Tentative #3: 2^2 * 1000ms = 4 secondes (succès?)
Abandon: Si echecs après 2 retries
```

**Avantages:**
- ✅ Réduction de charge sur le serveur
- ✅ Temps d'attente plus court pour les erreurs permanentes
- ✅ Plus de chance de succès pour les timeouts temporaires

---

## 🔒 Sécurité

### ✅ Token JWT
- Ajouté automatiquement par le JWT Interceptor
- Format: `Authorization: Bearer <token>`
- Validé côté backend

### ✅ Déconnexion automatique (401)
- Token expiré → Snackbar + Logout + Redirection login
- L'utilisateur ne voit pas la page protégée

### ✅ Gestion des permissions (403)
- Affiche un snackbar orange
- L'action n'est pas exécutée

---

## 📝 Customisation

### Ajouter un nouveau code d'erreur

```typescript
// Dans error.interceptor.ts, ajouter dans le switch:
case 418:
  errorMessage = 'I\'m a teapot';
  break;
```

### Customiser le message d'erreur

```typescript
case 500:
  errorMessage = 'Oops! Le serveur a eu un problème. Nous travaillons dessus!';
  break;
```

### Ajouter un avatar de charge

```typescript
case 504:
  snackBar.open(errorMessage, 'Réessayer', {
    duration: 8000,
    action: () => {
      // Re-émettre la requête
    }
  });
  break;
```

---

## 🚀 Optimisations futures

1. **Circuit Breaker**: Stopper les retries si trop d'erreurs d'affilée
2. **Exponential Backoff avec Jitter**: Éviter les pics
3. **Service Worker**: Cacher les réponses pour l'offline
4. **GraphQL avec Apollo Client**: Gestion d'erreur intégrée
5. **Sentry Integration**: Tracking d'erreurs en production

---

Generated: 2026-02-22 🏗️
