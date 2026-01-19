import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ins0119',
  templateUrl: './ins0119.component.html',
  styleUrls: ['./ins0119.component.css']
})
export class Ins0119Component implements OnInit {
  regForm!: FormGroup;
constructor(    private fb: FormBuilder,  // fb：用來建立表單
                private http: HttpClient, // http：用來呼叫 API
                private router: Router    // router：用來跳轉頁面
                ){}

   info = '';
   clear = '';

  //創建空陣列接收 後端的陣列 初始化下拉選單用
  list: { id: number; address: string }[] = [];
  ngOnInit(): void {
    this.regForm = this.fb.group({
          name: ['',Validators.required],
          account: ['',Validators.required],
          password: ['',Validators.required],
          phone: ['',Validators.required],
          email: ['',Validators.required],
          address: ['',Validators.required],
          zipcodes: [''],
          creUser: ['',Validators.required],
          creDate: ['',Validators.required],
          updUser: ['',Validators.required],
          updDate: ['',Validators.required]
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

//清除方法
clearForm() {
          this.regForm.reset({
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

          insert(){
              this.regForm.patchValue({ zipcodes: this.selectedZipcodes });//將多選框塞回表單再送出
              this.http.post('http://localhost:8080/user/0119/insert',this.regForm.value).subscribe({
              next:(res:any)=>{
              alert("新增成功");
              this.router.navigate(['/ind0119']);
              },
              error:(err)=>{console.log('失敗',err);this.info='新增失敗'}
              })
            }


}
