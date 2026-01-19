// // ComponentFixture：用來取得元件實例、操作 DOM、觸發變更偵測
// // TestBed：Angular 提供的測試環境建立工具
// import { ComponentFixture, TestBed } from '@angular/core/testing';
//
// // 匯入要測試的元件
// import { RegisterComponent } from './register.component';
//
//
// // describe：描述一組測試（這一組測試是針對 RegisterComponent）
// describe('RegisterComponent', () => {
//
//   // component：用來存放元件實例
//   let component: RegisterComponent;
//
//   // fixture：測試用的元件外殼（可以用來拿到 HTML、觸發 detectChanges）
//   let fixture: ComponentFixture<RegisterComponent>;
//
//   // beforeEach：每一個 it() 測試前都會先執行一次
//   beforeEach(async () => {
//
//     // 建立測試模組環境（模擬 Angular 的 module）
//     await TestBed.configureTestingModule({
//
//       // declarations：宣告這次測試會用到的元件（RegisterComponent）
//       declarations: [ RegisterComponent ]
//     })
//       // compileComponents：編譯元件的 template 與 css（測試前必做）
//       .compileComponents();
//
//     // 建立 RegisterComponent 的測試實例
//     fixture = TestBed.createComponent(RegisterComponent);
//
//     // 取得元件實體（component.ts 裡面的 class）
//     component = fixture.componentInstance;
//
//     // 觸發 Angular 變更偵測
//     // 會執行 ngOnInit()，並把畫面渲染出來
//     fixture.detectChanges();
//   });
//
//   // it：一個測試案例
//   it('should create', () => {
//
//     // expect：斷言（Assertion）
//     // toBeTruthy：代表 component 不是 null / undefined，確定有成功建立元件
//     expect(component).toBeTruthy();
//   });
// });
