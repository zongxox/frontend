import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';//建立表單
import { HttpClient, HttpErrorResponse } from '@angular/common/http';//http打api用
import { Router } from '@angular/router';//跳轉路徑用
import { ActivatedRoute } from '@angular/router';//讀取目前網址資訊的工具
@Component({
  selector: 'app-upd0119',
  templateUrl: './upd0119.component.html',
  styleUrls: ['./upd0119.component.css']
})
export class Upd0119Component implements OnInit {

    constructor(private route: ActivatedRoute,//把這個工具放進你的元件裡，讓你可以用 this.route 取得網址資料
                private fb: FormBuilder,  // fb：用來建立表單
                private http: HttpClient, // http：用來呼叫 API
                private router: Router    // router：用來跳轉頁面
      ) {}
    updForm!: FormGroup;
    id!: string;//聲明 id物件
    //創建空陣列接收 後端的陣列 初始化下拉選單用
    list: { id: number; address: string }[] = [];
    info='';//初始化提示
    clear = '';//清除提示
  ngOnInit(): void {
         this.id = this.route.snapshot.paramMap.get('id')!;//跳轉頁面抓到的id
            //初始化表單
            this.updForm = this.fb.group({
                  id:[],//在表單聲明id 會把上面的id物件放進去
                  name: [''],
                  account: [''],
                  password: [''],
                  phone: [''],
                  email: [''],
                  address: [''],
                  zipcodes: [''],
                  creUser: [''],
                  creDate: [''],
                  updUser: [''],
                  updDate: ['']
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

                //利用id查詢
                this.http.get(`http://localhost:8080/user/0119/updQuery/${this.id}`).subscribe({
                  next: (res:any) => {
                    this.updForm.patchValue(res);//將傳回來的值放入表單
                    //三元運算符號
                    //如果 res.zipcodes 有值（例如 "100"）
                    //就走前面
                    //如果 沒值（null/空的）
                    //就走後面 []
                    this.selectedZipcodes = res.zipcodes ? [String(res.zipcodes)] : [];
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
                    name: '',
                    account: '',
                    password: '',
                    phone: '',
                    email: '',
                    address: '',
                    zipcodes: '',
                    creUser: '',
                    creDate: '',
                    updUser: '',
                    updDate: '',
                  });
                  this.selectedZipcodes = [];
                  this.clear = '已清除';
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
              this.selectedZipcodes = this.selectedZipcodes.filter(x => x !== v);
            }
           console.log('目前選到的郵遞區號:', this.selectedZipcodes);
        }

      //修改按鈕
     update(){
         this.updForm.patchValue({ zipcodes: this.selectedZipcodes });
         this.updForm.patchValue({ id: this.id });
         this.http.post('http://localhost:8080/user/0119/update',this.updForm.value).subscribe({
         next:(res:any)=>{
         alert("修改成功");
         this.router.navigate(['/ind0119']);
         },
         error:(err)=>{console.log('失敗',err);this.info='新增失敗'}
         })
       }


}
