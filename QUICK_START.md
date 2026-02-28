# 🚀 Guide Rapide - Gestion des Erreurs MyFinApp

## Configuration en 3 étapes

### ✅ Étape 1: Vérifier que tout est en place

```bash
# Ces fichiers doivent exister:
✓ src/app/interceptor/error.interceptor.ts
✓ src/app/shared/services/error.service.ts
✓ src/app/app.config.ts (mis à jour)
✓ src/styles.css (mis à jour)
```

### ✅ Étape 2: Lancer l'application

```bash
cd myfin-app-front/src/webapp/my-angular-app
npm install
npm start
```

### ✅ Étape 3: Tester les erreurs

Ouvrir la console du navigateur (F12) et tester:

**Test 1: Erreur 401 (Session expirée)**
```typescript
// Dans la console:
// Tokens expira → Le snackbar s'affiche automatiquement
// Redirection vers /login
```

**Test 2: Erreur 500 (Serveur indisponible)**
```typescript
// L'intercepteur fait 2 retries automatiquement
// Snackbar: "Erreur serveur. Veuillez réessayer plus tard."
```

**Test 3: Erreur réseau**
```typescript
// Couper Internet → L'intercepteur fait 2 retries
// Snackbar: "Erreur de connexion. Vérifiez votre internet."
```

---

## 📊 Tableau de contrôle des erreurs

| Erreur | Snackbar | Action | Retry |
|--------|----------|--------|-------|
| 0 (réseau) | 🔴 Rouge | Affichage | ✅ 2x |
| 400 (validation) | 🔴 Rouge | Affichage | ❌ |
| 401 (auth) | 🔴 Rouge | Logout + Redirection | ❌ |
| 403 (permission) | 🟠 Orange | Affichage | ❌ |
| 404 (not found) | 🔴 Rouge | Affichage | ❌ |
| 500 (serveur) | 🔴 Rouge | Affichage | ✅ 2x |

---

## 💡 Tips

### 1. Désactiver temporairement l'intercepteur d'erreurs

Si tu as besoin de tester sans l'intercepteur:

```typescript
// Dans app.config.ts, commenter la ligne:
// withInterceptors([authInterceptor, errorInterceptor])
withInterceptors([authInterceptor]) // Enlever errorInterceptor
```

### 2. Ajouter des logs personnalisés

```typescript
// Dans un composant:
this.getData().subscribe({
  next: (data) => { console.log('Succès:', data); },
  error: (error) => {
    console.log('Erreur personnalisée:', error.status, error.message);
  }
});
```

### 3. Écouter les erreurs globalement

```typescript
import { ErrorService } from './shared/services/error.service';

constructor(private errorService: ErrorService) {
  // Afficher un badge avec le nombre d'erreurs
  this.errorService.error$.subscribe(error => {
    console.log('Erreur détectée:', error);
  });
}
```

---

## 🐛 Debugging

### Vérifier que l'intercepteur est actif

Ouvrir Network (F12) et vérifier:
- ✅ Les requêtes ont l'header `Authorization: Bearer TOKEN`
- ✅ Les réponses d'erreur affichent un snackbar

### Logs utiles

Dans la console (F12):
```javascript
// Voir tous les logs de l'intercepteur:
// "on est dans interceptor token ..."
// "[401] Unauthorized: ..."
```

---

## 📚 Fichiers modifiés

```
myfin-app-front/src/webapp/my-angular-app/
├── src/app/
│   ├── interceptor/
│   │   ├── jwt.interceptor.ts (inchangé)
│   │   └── error.interceptor.ts ✨ NOUVEAU
│   ├── shared/services/
│   │   └── error.service.ts ✨ NOUVEAU
│   ├── account-list/
│   │   └── account-list.component.ts 📝 MODIFIÉ (simplifié)
│   ├── app.config.ts 📝 MODIFIÉ (ajout errorInterceptor)
│   └── ...
├── src/styles.css 📝 MODIFIÉ (ajout .warning-snackbar, .info-snackbar)
└── ...
```

---

## 🎯 Prochaines étapes (optionnel)

1. **Ajouter un Dashboard d'erreurs**: Afficher l'historique des erreurs par code HTTP
2. **Ajouter des alertes**: Notifier un administrateur si trop d'erreurs 500
3. **Ajouter un cache**: Retry les requêtes échouées quand la connexion revient
4. **Analytics**: Tracker les erreurs les plus fréquentes

---

## ❓ FAQ

**Q: L'intercepteur d'erreurs s'applique-t-il à tous les appels HTTP?**
A: Oui, une fois configuré dans `app.config.ts`, il s'applique automatiquement.

**Q: Comment customiser les messages d'erreur?**
A: Éditer `error.interceptor.ts` dans la fonction `catchError`.

**Q: Est-ce que les composants doivent gérer les erreurs?**
A: Non, l'intercepteur gère 99% des cas. Les composants peuvent toujours écouter pour du debugging.

**Q: Peut-on désactiver l'intercepteur pour une requête?**
A: Oui, en utilisant un header personnalisé (à implémenter si besoin).

---

## 🆘 Support

Si tu rencontres des problèmes:
1. Vérifier la console (F12) pour les erreurs TypeScript
2. Vérifier que Material est installé (`npm ls @angular/material`)
3. Relancer l'application (`npm start`)
4. Vider le cache du navigateur (Ctrl+Shift+Del)

---

Generated: 2026-02-22 ✨
