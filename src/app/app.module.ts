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
//import { ReactiveFormsModule } from '@angular/forms';

// HTTP 模組：讓你可以用 HttpClient 去呼叫後端 API（GET/POST）
import { HttpClientModule } from '@angular/common/http';

// 你自己做的 Menu 元件（選單）
import { MenuComponent } from './menu/menu.component';

//Reactive Form 模組：讓你可以用 formGroup表單、formControlName標籤的name、Validators驗證規則
//FormsModule 給你在標籤用 [(ngModel)] 雙向綁定用的
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

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
//ngx-pagination
import { NgxPaginationModule } from 'ngx-pagination';

//PrimeNG
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { DropdownModule } from 'primeng/dropdown';


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
import { Ins0122Component } from './ins0122/ins0122.component';
import { Upd0122Component } from './upd0122/upd0122.component';
import { Ind0123Component } from './ind0123/ind0123.component';
import { Ins0123Component } from './ins0123/ins0123.component';
import { Upd0123Component } from './upd0123/upd0123.component';
import { Ind0126Component } from './ind0126/ind0126.component';
import { Ind0127Component } from './ind0127/ind0127.component';
import { Upd0127Component } from './upd0127/upd0127.component';
import { Ind0128Component } from './ind0128/ind0128.component';
import { Ind0129Component } from './ind0129/ind0129.component';
import { Upd0129Component } from './upd0129/upd0129.component';
import { Ind0130Component } from './ind0130/ind0130.component';
import { Upd0130Component } from './upd0130/upd0130.component';
import { Ind0202Component } from './ind0202/ind0202.component';
import { LoginComponent } from './login/login.component';
import { IndexComponent } from './index/index.component';
import { Ind0203Component } from './ind0203/ind0203.component';
import { Ind0204Component } from './ind0204/ind0204.component';
import { Ind0205Component } from './ind0205/ind0205.component';
import { Ind0206Component } from './ind0206/ind0206.component';


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
    Ind0122Component,
    Ins0122Component,
    Upd0122Component,
    Ind0123Component,
    Ins0123Component,
    Upd0123Component,
    Ind0126Component,
    Ind0127Component,
    Upd0127Component,
    Ind0128Component,
    Ind0129Component,
    Upd0129Component,
    Ind0130Component,
    Upd0130Component,
    Ind0202Component,
    LoginComponent,
    IndexComponent,
    Ind0203Component,
    Ind0204Component,
    Ind0205Component,
    Ind0206Component,      // 測試元件
  ],

  // imports：放「Angular 內建 / 外部提供的功能模組」
  // 例如：表單、路由、HTTP 功能
  imports: [
    NgxPaginationModule,
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
    HttpClientModule,
    BrowserAnimationsModule,     // HTTP 功能（打 API 用）
    FormsModule,
    TableModule,
    ButtonModule,
    InputTextModule,
    InputNumberModule,
    DropdownModule
  ],

  // providers：放「全域服務 Service」或依賴注入（通常可空）
  // 很多 service 用 providedIn: 'root' 就不用在這裡寫
  providers: [],

  // bootstrap：專案啟動時的第一個元件（入口）
  // Angular 會從 AppComponent 開始跑（對應 index.html 的 <app-root>）
  bootstrap: [AppComponent]
})
export class AppModule { }
