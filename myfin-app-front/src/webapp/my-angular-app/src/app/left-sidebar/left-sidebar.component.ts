import { Component } from '@angular/core';
import { WebSocketComponentComponent } from "../web-socket-component/web-socket-component.component";

@Component({
  selector: 'app-left-sidebar',
  imports: [WebSocketComponentComponent],
  templateUrl: './left-sidebar.component.html',
  styleUrl: './left-sidebar.component.css'
})
export class LeftSidebarComponent {

}
