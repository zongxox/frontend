import { AfterViewInit, Component } from '@angular/core';
import { initMenu, type MenuItem } from './menu/menu';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements AfterViewInit {
  title = 'frontend';

  ngAfterViewInit(): void {
    const menus: MenuItem[] = [
      {
        text: "開發測試",
        children: [
          { text: "Anguler+JDBC table:user DATE:0119", url: "http://localhost:4200/ind0119" },
          { text: "Anguler+JDBC table:fruit DATE:0120", url: "http://localhost:4200/ind0120" },
          { text: "Anguler+JDBC table:post DATE:0121", url: "http://localhost:4200/ind0121" },
          { text: "Anguler+JDBC table:post DATE:0122", url: "http://localhost:4200/ind0122" },
          { text: "Anguler+JDBC table:event_registration DATE:0123", url: "http://localhost:4200/ind0123" },
        ]
      }
    ];

    initMenu({
      mountSelector: '#menu',
      menus
    });
  }
}
