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
          { text: "Anguler+JDBC table:fruit DATE:0120", url: "http://localhost:4200/ind0120" }
        ]
      }
    ];

    initMenu({
      mountSelector: '#menu',
      menus
    });
  }
}
