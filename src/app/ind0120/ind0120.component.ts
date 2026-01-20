import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';//建立表單
import { HttpClient } from '@angular/common/http';//打api
import { Router } from '@angular/router';//url
@Component({
  selector: 'app-ind0120',
  templateUrl: './ind0120.component.html',
  styleUrls: ['./ind0120.component.css']
})

export class Ind0120Component implements OnInit {

  constructor(    private fb: FormBuilder,  // fb：用來建立表單
                  private http: HttpClient, // http：用來呼叫 API
                  private router: Router    // router：用來跳轉頁面
                  )
                  { }

  initForm!: FormGroup;
  info='';
  in='';
  del='';
  list:{id:number;origin:string}[]=[];
  ngOnInit(): void {
    this.initForm = this.fb.group({
            fruitName:[''],
            price:[''],
            quantity:[''],
            origin:['']
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

    //按鈕查詢
    fruitList:any[]=[];
    query(){
        const data ={
           fruitName: this.initForm.value.fruitName,
           price:this.initForm.value.price,
           quantity:this.selectedQuantity,
           id:this.initForm.value.origin,
          }
console.log('送出的 data:', data);
          this.http.post('http://localhost:8080/fruit/0120/query',data).subscribe({
            next:(res:any)=>{
              this.fruitList = res;
              this.in='表單查詢成功'
              console.log('res:', res);
              },
            error:(err)=>{console.log('失敗',err);this.in='表單查詢失敗'}
          })
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

    this.http.delete(`http://localhost:8080/fruit/0120/delete/${id}`).subscribe({
      next: () => {
        this.del = '刪除成功';
        this.query(); //刪除後重新查詢刷新表格
      },
      error: (err) => {
        console.log('刪除失敗', err);
        this.del = '刪除失敗';
      }
    });
  }

}
