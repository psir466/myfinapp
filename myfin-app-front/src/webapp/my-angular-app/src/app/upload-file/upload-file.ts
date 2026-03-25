import { Component } from '@angular/core';
import { FileBase64 } from '../model/account-model';
import { AccountServiceService } from '../account-service/account-service.service';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-upload-file',
  imports: [],
  templateUrl: './upload-file.html',
  styleUrl: './upload-file.css',
})
export class UploadFile {

  selectedFiles: File[] = [];

  	constructor(private accountService: AccountServiceService, private snackBar: MatSnackBar){}


  onFileSelected(event: any) {
      this.selectedFiles = Array.from(event.target.files);
    }


    uploadFiles() {
      const f64: FileBase64[] = [];

      const promises = this.selectedFiles.map((file) => {
        return new Promise<{ base64: string }>((resolve, reject) => {
          const reader = new FileReader();
          reader.onload = (e: any) => {
            const base64 = e.target.result.split(',')[1]; // Extract base64 part
            resolve({ base64: base64 });
          };
          reader.onerror = reject;
          reader.readAsDataURL(file);
        });
      });

      Promise.all(promises)
        .then((results) => {
          this.accountService.loadFiles(results).subscribe({
            next: (response) => {
              this.snackBar.open('Data loaded successfully!', 'Close', {
                duration: 3000,
                horizontalPosition: 'center',
                verticalPosition: 'bottom',
                panelClass: ['success-snackbar'],
              });
            },
            // L'erreur est désormais gérée par l'intercepteur d'erreurs global
            error: (error) => {
              console.log('Erreur capturée au niveau du composant:', error);
              // L'utilisateur a déjà reçu un snackbar du côté de l'intercepteur
            },
          });
        })
        .catch((promiseError) => {
          console.error('Error in Promise.all:', promiseError);
          this.snackBar.open('Error reading files. Please try again.', 'Close', {
            duration: 5000,
            horizontalPosition: 'center',
            verticalPosition: 'bottom',
            panelClass: ['error-snackbar'],
          });
        });
    }


}
