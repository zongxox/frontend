import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';//建立表單
import { HttpClient } from '@angular/common/http';//打api
import { Router } from '@angular/router';//url

@Component({
  selector: 'app-ins0121',
  templateUrl: './ins0121.component.html',
  styleUrls: ['./ins0121.component.css']
})
export class Ins0121Component implements OnInit {


  constructor(    private fb: FormBuilder,  // fb：用來建立表單
                  private http: HttpClient, // http：用來呼叫 API
                  private router: Router    // router：用來跳轉頁面
              )
             {}
    regForm!: FormGroup;
    list:{id:number;origin:string}[]=[];
    info='';
    clear='';
    //創建空陣列接收 後端的陣列 初始化下拉選單用
    authorList: { code: string; content: string }[] = [];
    statusList: { code: string; content: string }[] = [];
    categoryList: { code: string; content: string }[] = [];
  ngOnInit(): void {
             this.regForm = this.fb.group({
               title: ['', Validators.required],
               content: ['', Validators.required],
               author: ['', Validators.required],
               category: [[]],
               tags: ['', Validators.required],
               views: ['', Validators.required],
               likes: ['', Validators.required],
               status: ['', Validators.required],
               createdTime: ['', Validators.required],
               updatedTime: ['', Validators.required],
            });

             //初始化下拉式選單
             this.http.get('http://localhost:8080/codeType/0121/initSelect').subscribe({
               next:(res:any)=>{
                 console.log("res=", res);
                 console.log("keys=", Object.keys(res));
                 this.authorList = res.authorList;
                 this.statusList = res.statusList;
                 this.categoryList = res.categoryList
                 this.statusList = res.statusList.map((s: any) => ({
                   ...s,
                   content: s.code == '1' ? '公開' : '隱藏'
                 }));

                 this.info='初始化查詢成功'
                 },
               error:(err)=>{console.log('失敗',err);this.info='初始化查詢失敗'}
             })
  }



  //清除方法
  clearForm() {
            this.regForm.reset({
               title: '',
               content: '',
               author: '',
               category: '',
               tags: '',
               views: '',
               likes: '',
               status:  '',
               createdTime: '',
               updatedTime: ''
            });
            this.selectedCategory=[];
            this.clear = '已清除';
            }


            //聲明多選框陣列
             selectedCategory: string[] = [];

             //抓取多選框
             getCategory(event: any): void {
               const v = event.target.value;      // 100/220/320
               const c = event.target.checked;    // true/false

               if(c){//true 或 false
                   //selectedZipcodes 是一個陣列
                   this.selectedCategory.push(v); //在陣列裡面加入value值
                 }else{
                   //v是使用者選到的值
                   //filter 是過濾器
                   //x => x !== v
                   //x的意思是selectedZipcodes陣列裡面的[x:x:x]
                   //v是畫面選到的 value
                   //x = "100" → "100" !== "220" 留下
                   //x = "220" → "220" !== "220" 丟掉
                   //x = "320" → "320" !== "220" 留下
                   this.selectedCategory = this.selectedCategory.filter(x => x !== v);
                 }
                console.log(this.selectedCategory);
             }

         //新增按鈕
           insert(){
              this.regForm.patchValue({ category: this.selectedCategory });//將多選框塞回表單再送出
              console.log('payload=', this.regForm.value);
               this.http.post('http://localhost:8080/post/0121/insert',this.regForm.value).subscribe({
               next:(res:any)=>{
                  console.log('payload=', this.regForm.value);
               alert("新增成功");
               this.router.navigate(['/ind0121']);
               },
               error:(err)=>{console.log('失敗',err);this.info='新增失敗'}
               })
             }


}
