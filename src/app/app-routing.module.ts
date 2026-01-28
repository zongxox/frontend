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
import { Ins0122Component } from './ins0122/ins0122.component';
import { Upd0122Component } from './upd0122/upd0122.component';
import { Ind0123Component } from './ind0123/ind0123.component';
import { Ins0123Component } from './ins0123/ins0123.component';
import { Upd0123Component } from './upd0123/upd0123.component';
import { Ind0126Component } from './ind0126/ind0126.component';
import { Ind0127Component } from './ind0127/ind0127.component';
import { Upd0127Component } from './upd0127/upd0127.component';
import { Ind0128Component } from './ind0128/ind0128.component';
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
  { path: 'ins0122', component: Ins0122Component },
  { path: 'upd0122/:id', component: Upd0122Component },
  { path: 'ind0123', component: Ind0123Component },
  { path: 'ins0123', component: Ins0123Component },
  { path: 'upd0123/:memberId', component: Upd0123Component },
  { path: 'ind0126', component: Ind0126Component },
  { path: 'ind0127', component: Ind0127Component },
  { path: 'upd0127/:id', component: Upd0127Component },
  { path: 'ind0128', component: Ind0128Component },
];

@NgModule({
  // forRoot(routes) 代表：在整個專案的根模組啟用路由，並套用上面 routes 的規則
  imports: [RouterModule.forRoot(routes)],

  // export RouterModule 讓其他地方也能用路由功能（像 routerLink、router-outlet）
  exports: [RouterModule]
})
export class AppRoutingModule { }
