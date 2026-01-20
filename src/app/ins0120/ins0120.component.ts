import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';//建立表單
import { HttpClient } from '@angular/common/http';//打api
import { Router } from '@angular/router';//url

@Component({
  selector: 'app-ins0120',
  templateUrl: './ins0120.component.html',
  styleUrls: ['./ins0120.component.css']
})
export class Ins0120Component implements OnInit {

  constructor(    private fb: FormBuilder,  // fb：用來建立表單
                  private http: HttpClient, // http：用來呼叫 API
                  private router: Router    // router：用來跳轉頁面
                  )
                  { }


  regForm!: FormGroup;
  list:{id:number;origin:string}[]=[];
  info='';
  clear='';
  ngOnInit(): void {
       this.regForm = this.fb.group({
          fruitName: ['',Validators.required],   // 水果名稱
          fruitCode: ['',Validators.required],   // 水果編號
          fruitType: ['',Validators.required],   // 水果種類
          price: ['',Validators.required],       // 價格 radio
          quantity:[],
          origin: ['',Validators.required],      // 產地 select
          remark: ['',Validators.required],      // 備註
          creUser: ['',Validators.required],     // 建立人
          creDate: ['',Validators.required],     // 建立時間 datetime-local
          updUser: ['',Validators.required],     // 更新人
          updDate: ['',Validators.required],     // 更新時間 datetime-local
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
  }


//清除方法
clearForm() {
          this.regForm.reset({
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




  //新增按鈕
  insert(){
      this.regForm.patchValue({ quantity: this.selectedQuantity });//將多選框塞回表單再送出
      this.http.post('http://localhost:8080/fruit/0120/insert',this.regForm.value).subscribe({
      next:(res:any)=>{
      alert("新增成功");
      this.router.navigate(['/ind0120']);
      },
      error:(err)=>{console.log('失敗',err);this.info='新增失敗'}
      })
    }

}
