// Angular 模組的核心功能
import { NgModule } from '@angular/core';

// RouterModule: 路由功能 / Routes: 路由規則的型別
import { RouterModule, Routes } from '@angular/router';

// 引入註冊頁元件
import { Ind0119Component } from './ind0119/ind0119.component';
import { Ins0119Component } from './ins0119/ins0119.component';
import { Upd0119Component } from './upd0119/upd0119.component';

// 路由規則設定區：
// 當網址符合 path，就會顯示對應的 component 在 <router-outlet> 裡面
const routes: Routes = [
  // 當使用者進入 /register 時，就顯示 RegisterComponent（註冊頁）
  { path: 'ind0119', component: Ind0119Component },
  { path: 'ins0119', component: Ins0119Component },
  { path: 'upd0119/:id', component: Upd0119Component },
];

@NgModule({
  // forRoot(routes) 代表：在整個專案的根模組啟用路由，並套用上面 routes 的規則
  imports: [RouterModule.forRoot(routes)],

  // export RouterModule 讓其他地方也能用路由功能（像 routerLink、router-outlet）
  exports: [RouterModule]
})
export class AppRoutingModule { }
