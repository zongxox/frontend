import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';//建立表單
import { HttpClient } from '@angular/common/http';//打api
import { Router } from '@angular/router';//url
import { ActivatedRoute } from '@angular/router';//讀取目前網址資訊的工具
@Component({
  selector: 'app-upd0120',
  templateUrl: './upd0120.component.html',
  styleUrls: ['./upd0120.component.css']
})
export class Upd0120Component implements OnInit {

  constructor(    private fb: FormBuilder,  // fb：用來建立表單
                  private http: HttpClient, // http：用來呼叫 API
                  private router: Router,    // router：用來跳轉頁面
                  private route: ActivatedRoute,//把這個工具放進你的元件裡，讓你可以用 this.route 取得網址資料
                  )
                  { }
  updForm!: FormGroup;
  list:{id:number;origin:string}[]=[];
  id!:string;
  info='';
  clear='';
  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id')!;
           this.updForm = this.fb.group({
              id:[''],
              fruitName: [''],   // 水果名稱
              fruitCode: [''],   // 水果編號
              fruitType: [''],   // 水果種類
              price: [''],       // 價格 radio
              quantity:[],
              origin: [''],      // 產地 select
              remark: [''],      // 備註
              creUser: [''],     // 建立人
              creDate: [''],     // 建立時間 datetime-local
              updUser: [''],     // 更新人
              updDate: [''],     // 更新時間 datetime-local

            });

           //初始化下拉式選單
           this.http.get('http://localhost:8080/fruit/0120/initSelect').subscribe({
             next:(res:any)=>{
               this.list = res;
               console.log(this.list);
               this.info='初始化查詢成功'
               },
             error:(err)=>{console.log('失敗',err);this.info='初始化查詢失敗'}
           })

          //利用id查詢
          this.http.post('http://localhost:8080/fruit/0120/updQuery/',{ id:this.id}).subscribe({
            next: (res:any) => {
              this.updForm.patchValue(res);//將傳回來的值放入表單
              //三元運算符號
              //如果 res.zipcodes 有值（例如 "100"）
              //就走前面
              //如果 沒值（null/空的）
              //就走後面 []
              this.selectedQuantity = res.quantity ? [String(res.quantity)] : [];
              console.log(res);
              this.info='查詢成功';
            },
            error: (err) => {
              console.log('查詢失敗', err);
              this.info = '查詢失敗';
            }
          });

  }

  //清除方法
  clearForm() {
            this.updForm.reset({
             fruitName: '',   // 水果名稱
             fruitCode: '',   // 水果編號
             fruitType: '',   // 水果種類
             price: '',       // 價格 radio
             quantity:'',
             origin: '',      // 產地 select
             remark: '',      // 備註
             creUser: '',     // 建立人
             creDate: '',     // 建立時間 datetime-local
             updUser: '',     // 更新人
             updDate: '',     // 更新時間 datetime-local
            });
            this.selectedQuantity = [];
            this.clear = '已清除';
            }


           //聲明多選框陣列
           selectedQuantity: string[] = [];

           //抓取多選框
           getQuantity(event: any): void {
             const v = event.target.value;      // 100/220/320
             const c = event.target.checked;    // true/false

             if(c){//true 或 false
                 //selectedZipcodes 是一個陣列
                 this.selectedQuantity.push(v); //在陣列裡面加入value值
               }else{
                 this.selectedQuantity = this.selectedQuantity.filter(x => x !== v);
               }
              console.log('目前選到的:', this.selectedQuantity);
           }


          //修改按鈕
          update(){
              this.updForm.patchValue({ quantity: this.selectedQuantity });
              this.updForm.patchValue({ id: this.id });
              this.http.post('http://localhost:8080/fruit/0120/update',this.updForm.value).subscribe({
              next:(res:any)=>{
              alert("修改成功");
              this.router.navigate(['/ind0120']);
              },
              error:(err)=>{console.log('失敗',err);this.info='新增失敗'}
              })
            }

          //下載檔案
          don() {
            this.http.get(`http://localhost:8080/fruit/0120/download/${this.id}`, {
              observe: 'response',
              // 告訴 Angular：這次回來的不是 JSON，而是「檔案資料」(二進位)
              // 如果不寫 blob，Angular 會當 JSON 解析，通常就會失敗跑到 error
              responseType: 'blob'
            }).subscribe({
              next: (resp) => {
                //取出後端回傳的檔案本體 (Blob = 一包二進位檔案資料，例如 zip/pdf/jpg)
                const blob = resp.body!;

                //把這包 Blob 轉成瀏覽器可以使用的「暫時網址」(存在記憶體裡的 URL)
                //例如會變成 blob:http://localhost:4200/xxxxxxx...
                const url = window.URL.createObjectURL(blob);

                //動態建立一個 <a> 超連結標籤（但不會顯示在畫面上）
                const a = document.createElement('a');

                //把超連結的 href 指向剛剛產生的 blob URL
                //等於按下去就會抓這包 Blob 內容
                a.href = url;

                //模擬使用者點擊這個連結
                //這一步會觸發瀏覽器的下載動作（或開啟檔案，取決於瀏覽器設定）
                a.click();

                //釋放剛剛建立的 blob URL（避免記憶體一直累積佔用）
                window.URL.revokeObjectURL(url);
              },
            error: async (err: any) => {
              console.log('下載 error 物件=', err);
              }
            });
          }


}
