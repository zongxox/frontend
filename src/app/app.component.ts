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
          { text: "登入及註冊DATE:0131", url: "http://localhost:4200/login0131" },
          { text: "Anguler+JDBC table:user DATE:0119", url: "http://localhost:4200/ind0119" },
          { text: "Anguler+JDBC table:fruit DATE:0120", url: "http://localhost:4200/ind0120" },
          { text: "Anguler+JDBC table:post DATE:0121", url: "http://localhost:4200/ind0121" },
          { text: "Anguler+JDBC table:post DATE:0122", url: "http://localhost:4200/ind0122" },
          { text: "Anguler+JDBC table:event_registration DATE:0123", url: "http://localhost:4200/ind0123" },
          { text: "Anguler+JDBC table:OrderItem DATE:0126", url: "http://localhost:4200/ind0126" },
          { text: "Anguler+JDBC table:user DATE:0127", url: "http://localhost:4200/ind0127" },
          { text: "Anguler+JPA table:Records DATE:0128", url: "http://localhost:4200/ind0128" },
          { text: "Anguler+JPA table:Records DATE:0129", url: "http://localhost:4200/ind0129" },
          { text: "Anguler+JPA table:Articles DATE:0130", url: "http://localhost:4200/ind0130" },
          { text: "Anguler+JPA table:Course DATE:0202", url: "http://localhost:4200/ind0202" },
        ]
      }
    ];

    initMenu({
      mountSelector: '#menu',
      menus
    });
  }
}
