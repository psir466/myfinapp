import { Injectable } from '@angular/core';
import { Client, Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  private baseUrl = 'http://finapp.local/backfront'; // URL de votre BoF
  private stompClient: Client;
  private isConnected = new BehaviorSubject<boolean>(false);
  private dataSubject = new BehaviorSubject<any>(null);

  constructor() {
    this.stompClient = new Client({
      webSocketFactory: () => {
        return new SockJS(this.baseUrl + '/ws'); // Assurez-vous que l'URL correspond à votre configuration Spring Boot
      },
      onConnect: () => {
        console.log('Connecté au serveur WebSocket!');
        this.isConnected.next(true);

        this.stompClient.subscribe('/topic/data-updates', (message) => {
          console.log(message.body);
          const updatedData = JSON.parse(message.body);
          this.dataSubject.next(updatedData);
          console.log('Donnée mise à jour reçue:', updatedData);
        });
      },
      onDisconnect: () => {
        console.log('Déconnecté du serveur WebSocket');
        this.isConnected.next(false);
      }
    });

    this.stompClient.activate();
  }

  public getUpdatedData(): Observable<any> {
    return this.dataSubject.asObservable();
  }
}
