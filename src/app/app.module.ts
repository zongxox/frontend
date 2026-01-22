// Angular 核心功能：NgModule 是用來宣告「這個模組」用的
import { NgModule } from '@angular/core';

// 讓 Angular 可以在瀏覽器環境執行（一般網站必備）
import { BrowserModule } from '@angular/platform-browser';

// 專案的路由設定（網址跳轉 / 換頁）
import { AppRoutingModule } from './app-routing.module';

// 整個 Angular 專案的根元件（<app-root></app-root>）
import { AppComponent } from './app.component';

// 你自己做的註冊元件（註冊頁）
import { Ind0119Component } from './ind0119/ind0119.component';

// Reactive Form 模組：讓你可以用 formGroup、formControlName、Validators
import { ReactiveFormsModule } from '@angular/forms';

// HTTP 模組：讓你可以用 HttpClient 去呼叫後端 API（GET/POST）
import { HttpClientModule } from '@angular/common/http';

// 你自己做的 Menu 元件（選單）
import { MenuComponent } from './menu/menu.component';

//Angular Material
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatRadioModule } from '@angular/material/radio';



// 你自己做的 Test 元件（測試頁）
import { Ins0119Component } from './ins0119/ins0119.component';
import { Upd0119Component } from './upd0119/upd0119.component';
import { Ind0120Component } from './ind0120/ind0120.component';
import { Ins0120Component } from './ins0120/ins0120.component';
import { Upd0120Component } from './upd0120/upd0120.component';
import { Ind0121Component } from './ind0121/ind0121.component';
import { Ins0121Component } from './ins0121/ins0121.component';
import { Upd0121Component } from './upd0121/upd0121.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Ind0122Component } from './ind0122/ind0122.component';


// @NgModule 是 Angular 的「模組設定」
// 用來告訴 Angular：有哪些元件、有哪些功能要載入、專案從哪裡開始跑
@NgModule({

  // declarations：放「你自己建立的元件 Component」
  // 這裡宣告之後，HTML 才能使用 <app-register> 這種標籤
  declarations: [
    AppComponent,       // 根元件：app-root
    Ind0119Component,  // 註冊元件
    MenuComponent,      // 選單元件
    Ins0119Component,
    Upd0119Component,
    Ind0120Component,
    Ins0120Component,
    Upd0120Component,
    Ind0121Component,
    Ins0121Component,
    Upd0121Component,
    Ind0122Component,      // 測試元件
  ],

  // imports：放「Angular 內建 / 外部提供的功能模組」
  // 例如：表單、路由、HTTP 功能
  imports: [
    MatRadioModule,
    MatCardModule,
    MatDividerModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatCheckboxModule,
    MatButtonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSnackBarModule,
    BrowserModule,       // 讓 Angular 在瀏覽器跑（必備）
    AppRoutingModule,    // 路由功能（頁面跳轉）

    ReactiveFormsModule, // Reactive Form 功能（formGroup/formControlName）
    HttpClientModule, BrowserAnimationsModule     // HTTP 功能（打 API 用）
  ],

  // providers：放「全域服務 Service」或依賴注入（通常可空）
  // 很多 service 用 providedIn: 'root' 就不用在這裡寫
  providers: [],

  // bootstrap：專案啟動時的第一個元件（入口）
  // Angular 會從 AppComponent 開始跑（對應 index.html 的 <app-root>）
  bootstrap: [AppComponent]
})
export class AppModule { }
