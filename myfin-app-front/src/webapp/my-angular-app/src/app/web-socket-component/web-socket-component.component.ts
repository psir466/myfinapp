import { Component, OnInit } from '@angular/core';
import { WebsocketService } from '../web-socket-service/websocket.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-web-socket-component',
  imports: [],
  templateUrl: './web-socket-component.component.html',
  styleUrl: './web-socket-component.component.css'
})
export class WebSocketComponentComponent {

  coursValue: any;
  varValue: any;
  private dataSubscription: Subscription | undefined;

  constructor(private websocketService: WebsocketService) {}

  ngOnInit(): void {


   this.coursValue = localStorage.getItem('cours');
   this.varValue = localStorage.getItem('variation');

   this.dataSubscription = this.websocketService.getUpdatedData().subscribe(
      (updatedData) => {
        console.log("donnée reçu dans le composant WebSocket " + updatedData);
        if (updatedData) {
          this.coursValue = updatedData.cours;
          this.varValue = updatedData.variation;
          localStorage.setItem('cours', updatedData.cours);
          localStorage.setItem('variation', updatedData.variation);
          console.log("donnée reçu pour affichage " + this.coursValue + " " + this.varValue);
        }
      }
    );
  }

  ngOnDestroy(): void {
    // N'oubliez pas de vous désabonner pour éviter les fuites de mémoire
    if (this.dataSubscription) {
      this.dataSubscription.unsubscribe();
    }
  }

}
