// Angular 模組的核心功能
import { NgModule } from '@angular/core';

// RouterModule: 路由功能 / Routes: 路由規則的型別
import { RouterModule, Routes } from '@angular/router';

// 引入註冊頁元件
import { Ind0119Component } from './ind0119/ind0119.component';
import { Ins0119Component } from './ins0119/ins0119.component';
import { Upd0119Component } from './upd0119/upd0119.component';
import { Ind0120Component } from './ind0120/ind0120.component';
import { Ins0120Component } from './ins0120/ins0120.component';
import { Upd0120Component } from './upd0120/upd0120.component';
import { Ind0121Component } from './ind0121/ind0121.component';
import { Ins0121Component } from './ins0121/ins0121.component';
import { Upd0121Component } from './upd0121/upd0121.component';
import { Ind0122Component } from './ind0122/ind0122.component';
// 路由規則設定區：
// 當網址符合 path，就會顯示對應的 component 在 <router-outlet> 裡面
const routes: Routes = [
  // 當使用者進入 /register 時，就顯示 RegisterComponent（註冊頁）
  { path: 'ind0119', component: Ind0119Component },
  { path: 'ins0119', component: Ins0119Component },
  { path: 'upd0119/:id', component: Upd0119Component },
  { path: 'ind0120', component: Ind0120Component },
  { path: 'ins0120', component: Ins0120Component },
  { path: 'upd0120/:id', component: Upd0120Component },
  { path: 'ind0121', component: Ind0121Component },
  { path: 'ins0121', component: Ins0121Component },
  { path: 'upd0121/:id', component: Upd0121Component },
  { path: 'ind0122', component: Ind0122Component },
];

@NgModule({
  // forRoot(routes) 代表：在整個專案的根模組啟用路由，並套用上面 routes 的規則
  imports: [RouterModule.forRoot(routes)],

  // export RouterModule 讓其他地方也能用路由功能（像 routerLink、router-outlet）
  exports: [RouterModule]
})
export class AppRoutingModule { }
