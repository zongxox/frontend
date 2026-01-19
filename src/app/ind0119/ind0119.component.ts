//Angular 核心功能：Component 告訴Angular 這是ts用來宣告元件
//OnInit 用來使用 ngOnInit() 初始化流程 Angular內建生命週期函式
//ngOnInit() 觸發時機：畫面剛載入這個元件時（只會跑一次）
//ngOnDestroy() 觸發時機：你離開這頁、切換路由、元件被移除時
//ngOnChanges() 觸發時機：父元件傳進來的資料改變時
import { Component, OnInit } from '@angular/core';

// Reactive Form 需要的工具：
//FormBuilder 用來快速建立表單、FormGroup 代表整張表單、Validators 是驗證規則(必填等等)
//FormGroup = [formGroup]="regForm"
//Validators.required 必填
//Validators.minLength(6) 至少6碼
//Validators.email Email格式
// this.regForm = this.fb.group({
//   name: ['', [Validators.required, Validators.minLength(2)]],     // 必填 + 至少2字
//   phone: ['', Validators.required],                               // 必選
//   address: ['', Validators.required],                             // 必選
//   companyId: [[], Validators.required]                            // 至少要勾一個（陣列不可空）
// });
import { FormBuilder, FormGroup, Validators } from '@angular/forms';



// HttpClient 用來呼叫後端 API（例如 POST 註冊資料）
//我要使用 Angular 內建的「HttpClient」這個工具，來跟後端 API 溝通（送資料 / 拿資料）
//類似Axios 要導入這個物件才能使用
//reg() {
//     const payload = this.regForm.value;
//
//     this.http.post('http://localhost:8080/user/save', payload).subscribe({
//       next: (res) => {
//         this.info = '註冊成功';
//       },
//       error: (err) => {
//         this.info = '註冊失敗';
//       }
//     });
//   }
import { HttpClient } from '@angular/common/http';



//<button type="button"  routerLink="/ins0119">新增</button> 按鈕點擊事件,呼叫方法跳轉畫面
import { Router } from '@angular/router';


// @Component 是 Angular 的「元件設定」
//告訴 Angular：這支 TS 是一個元件(Component)，並且它的畫面跟樣式在哪裡。
@Component({
  // selector：這個元件可以用 <app-register></app-register> 顯示在頁面上
  selector: 'app-register',

  // templateUrl：這個元件畫面的 HTML 檔案位置
  templateUrl: './ind0119.component.html',

  // styleUrls：這個元件專用的 CSS 檔案位置
  styleUrls: ['./ind0119.component.css']
})


//建立class 方法都在裡面執行
// RegisterComponent：這是 表單的 TypeScript 控制中心
// implements OnInit：表示這個元件會使用 ngOnInit() 這個方法
export class Ind0119Component implements OnInit {

  // regForm：整張表單(FormGroup)，對應 HTML 的 [formGroup]="regForm"
  // ! 代表：我晚一點一定會初始化它（通常在 ngOnInit 裡建立）
  //如果不加上! 直接聲明會報錯誤
  //?：可能有值，也可能沒有（要先判斷）
  initForm!: FormGroup;



  // info：給畫面顯示提示訊息用（例如：註冊成功 / 註冊失敗）
  // 對應 HTML：<p *ngIf="info">{{ info }}</p>
  info = '';
  in  = '';

  // constructor：Angular 會在建立元件時自動幫你注入(準備)這些工具
  constructor(
    private fb: FormBuilder,  // fb：用來建立表單
    private http: HttpClient, // http：用來呼叫 API
    private router: Router    // router：用來跳轉頁面
  ) {}


  //創建空陣列接收 後端的陣列 初始化下拉選單用
  list: { id: number; address: string }[] = [];

  // ngOnInit：元件一出現在畫面上就會自動執行一次
  // 通常用來「初始化表單、載入資料、設定預設值」
  ngOnInit(): void {
    this.initForm = this.fb.group({
                    name: [''], //''預設值
                    phone:[''],//單選框預設值
                    address: [''],//下拉是選單
                    //設置 Validators.required驗證格式 讓使用者 不選擇的話 就不開啟查詢按鈕
                    zipcodes:[[]]//下拉是選單 這裡不是用formControlName抓值
    });


      //初始化下拉式選單
      this.http.get('http://localhost:8080/user/0119/initSelect').subscribe({
        next:(res:any)=>{
          this.list = res;
          console.log(this.list);
          this.info='初始化查詢成功'
          },
        error:(err)=>{console.log('失敗',err);this.info='初始化查詢失敗'}
      })

  }

  //聲明多選框陣列
  selectedZipcodes: string[] = [];

  //抓取多選框
  getZipcodes(event: any): void {
    const v = event.target.value;      // 100/220/320
    const c = event.target.checked;    // true/false

    if(c){//true 或 false
        //selectedZipcodes 是一個陣列
        this.selectedZipcodes.push(v); //在陣列裡面加入value值
      }else{
        //v是使用者選到的值
        //filter 是過濾器
        //x => x !== v
        //x的意思是selectedZipcodes陣列裡面的[x:x:x]
        //v是畫面選到的 value
        //x = "100" → "100" !== "220" 留下
        //x = "220" → "220" !== "220" 丟掉
        //x = "320" → "320" !== "220" 留下
        this.selectedZipcodes = this.selectedZipcodes.filter(x => x !== v);
      }
  }


  //聲明空陣列 接收後端傳地查詢過來的陣列物件
  userList: any[] = [];
  //點擊查詢按鈕後會執行的方法
  query(): void {
        const data = {
            name: this.initForm.value.name,
            phone: this.initForm.value.phone,
            id: this.initForm.value.address,
            zipcodes: this.selectedZipcodes
          };

          this.http.post('http://localhost:8080/user/0119/query', data).subscribe({
            next: (res: any) => {
              this.userList = res;
              console.log("查詢成功:", res);
              this.in  = "查詢成功";
            },
            error: (err) => {
              console.log("查詢失敗:", err);
              this.in  = "查詢失敗";
            }
          });
  }

  //修改只顯示時間
  approveDateStart(data: string) {
    if (!data) return '';
    // "2026-01-17 17:51:05"
    const [, time] = data.split('T');
    return time;//顯示00:00:00
  }

  //修改
  approveDate(data: string) {
    if (!data) return '';
    // "2026-01-17 17:51:05"
    const [date, time] = data.split('T');
    return `${date} ${time}`;//顯示2026-01-17 17:51:05
  }


  //刪除
  delete(id: string) {
    if (!confirm('確定要刪除嗎？')) return;

    this.http.delete(`http://localhost:8080/user/0119/delete/${id}`).subscribe({
      next: () => {
        this.in = '刪除成功';
        this.query(); //刪除後重新查詢刷新表格
      },
      error: (err) => {
        console.log('刪除失敗', err);
        this.in = '刪除失敗';
      }
    });
  }



}

