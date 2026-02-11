import { AfterViewInit, Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { filter } from 'rxjs/operators';

import { initMenu, type MenuItem } from './menu/menu';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements AfterViewInit {
  title = 'frontend';

  showMenu = true;

  private menus: MenuItem[] = [
    {
      text: "開發測試",
      children: [
        { text: "Anguler+JDBC table:user DATE:0119", url: "/ind0119" },
        { text: "Anguler+JDBC table:fruit DATE:0120", url: "/ind0120" },
        { text: "Anguler+JDBC table:post DATE:0121", url: "/ind0121" },
        { text: "Anguler+JDBC table:post DATE:0122", url: "/ind0122" },
        { text: "Anguler+JDBC table:event_registration DATE:0123", url: "/ind0123" },
        { text: "Anguler+JDBC table:OrderItem DATE:0126", url: "/ind0126" },
        { text: "Anguler+JDBC table:user DATE:0127", url: "/ind0127" },
        { text: "Anguler+JPA table:Records DATE:0128", url: "/ind0128" },
        { text: "Anguler+JPA table:Records DATE:0129", url: "/ind0129" },
        { text: "Anguler+JPA table:Articles DATE:0130", url: "/ind0130" },
        { text: "Anguler+JPA table:Course DATE:0202", url: "/ind0202" },
        { text: "CRUD poi上傳下載 跨域加上排序 DATE:0203", url: "/ind0203" },
        { text: "CRUD poi上傳下載 pdf DATE:0204", url: "/ind0204" },
        { text: "CRUD poi上傳下載  pdf DATE:0205", url: "/ind0205" },
        { text: "CRUD poi上傳下載  pdf DATE:0209", url: "/ind0206" },
        { text: "CRUD poi上傳下載  pdf DATE:0210", url: "/ind0210" },
        { text: "CRUD poi上傳下載  pdf DATE:0211", url: "/ind0211" },
        { text: "CRUD poi上傳下載  pdf DATE:0211", url: "/ind0212" },
      ]
    }
  ];

  constructor(private router: Router,private http: HttpClient) {
    // 只要路由變更就判斷要不要顯示 menu
    this.router.events
      .pipe(filter(e => e instanceof NavigationEnd))
      .subscribe(() => {
        const url = this.router.url.split('?')[0];
        this.showMenu = url !== '/login';

        // 如果不是 login，等 DOM 把 #menu 放出來後再初始化
        if (this.showMenu) {
          setTimeout(() => {
            initMenu({
              mountSelector: '#menu',
              menus: this.menus
            });
          });
        }
      });
  }

  ngAfterViewInit(): void {
    // 第一次載入也判斷一次
    const url = this.router.url.split('?')[0];
    this.showMenu = url !== '/login';

    if (this.showMenu) {
      initMenu({
        mountSelector: '#menu',
        menus: this.menus
      });
    }
  }

logout() {
  this.http.post('http://localhost:8080/logout',{},{ withCredentials: true }).subscribe({
    next: () => {
      this.router.navigateByUrl('/login');
    },
    error: () => {
      // 就算後端失敗，也直接導回登入頁
      this.router.navigateByUrl('/login');
    }
  });
}



}
