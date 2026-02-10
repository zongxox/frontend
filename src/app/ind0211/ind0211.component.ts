import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ind0211',
  templateUrl: './ind0211.component.html',
  styleUrls: ['./ind0211.component.css']
})
export class Ind0211Component implements OnInit {


  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}
  initForm!:FormGroup;
  statusList:{status:string;statusName:string}[] = [];
  sourceList:{source:string}[]=[];
  orderTypeList:{orderType:string;}[]=[];
  page=1;
  pageSize=5;
  info="";
  ins="";
  ordersList:any[]=[];
  ngOnInit(): void {
    this.initForm = this.fb.group({
      source:[''],
      customer:[''],
      status:[''],
      orderType:[]
    });
    this.http.get('http://localhost:8080/Orders/0211/init',{ withCredentials: true }).subscribe({
          next:(res:any)=>{
            this.statusList = this.uniqBy(res,x=>x.status);
            this.sourceList = this.uniqBy(res, x =>x.source);
            this.orderTypeList = this.uniqBy(res,x=>x.orderType);
          },error:(err:any)=>{
            console.log('失敗', err);
            this.router.navigateByUrl('/');   // 這會回到 http://localhost:4200/
          }
        })

  }

//單選框 跟 多選框 跟  下拉式 去除重複
  private uniqBy<T>(arr: T[], keyFn: (x: T) => string) : T[] {
    const map = new Map<string, T>();
    for (const item of arr || []) {
      const key = keyFn(item);
      if (key != null && !map.has(key)) map.set(key, item);
    }
    return Array.from(map.values());
  }



    //聲明多選框陣列
    selectOrderType: string[] = [];

    //抓取多選框
    getOrderType(event: any): void {
      const v = event.target.value;      // 100/220/320
      const c = event.target.checked;    // true/false

      if(c){//true 或 false
          //selectedZipcodes 是一個陣列
          this.selectOrderType.push(v); //在陣列裡面加入value值
        }else{
          this.selectOrderType = this.selectOrderType.filter(x => x !== v);
        }
       console.log('目前選到的:', this.selectOrderType);
    }

  //去掉T 把-改/
    approveDateStart(data: string) {
      if (!data) return '';
      // 支援 'T' 或 空白
      const [date, time] = data.includes('T')? data.split('T'): data.split(' ');
          return `${date} ${time}`;
    }


//表格 開關
editingId: number | null = null;
editModel: any = null;

startEdit(orders: any) {
  this.editingId = orders.id;
  //把目前這一筆 course 的資料
  // 複製一份出來
  //存到 editModel 裡面，當作「編輯用的資料」。
  this.editModel = { ...orders };

  //轉成陣列,讓畫面去勾選
  this.selectOrderType = orders.orderType
  ? String(orders.orderType).split(',').map((x: string) => x.trim())
  : [];


  console.log('進入編輯，初始化 selectLocation =', this.selectOrderType);
}


cancelEdit() {
  this.editingId = null;
  this.editModel = null;
}

//查詢按鈕
    noData = false;
    query(){
     let value = this.initForm.value.customer;
     if (!value) {
       value = '';
     }

     // 可空，但只要有值就必須是中文
     if (value !== '' && !/^[A-Za-z\u4e00-\u9fa5]+$/.test(value)) {
        this.info = '客戶名稱不能輸入數字';
           setTimeout(() => {
             this.info = '';
           }, 2000);
           return;
     }

  const data = {
        source : this.initForm.value.source,
        customer : this.initForm.value.customer,
        status : this.initForm.value.status,
        orderType : this.selectOrderType
        }

      this.http.post('http://localhost:8080/Orders/0211/query',data,{ withCredentials: true }).subscribe({
        next:(res:any)=>{
          this.ordersList = res;

          this.noData = res.length === 0;//後端回傳的資料比較後如果是0,就給到noData
          if (this.noData) {
            this.info = '查無符合條件的資料!!';
            setTimeout(() => {
              this.info = '';
            }, 2000);
          return;
          }

        },error:(err:any)=>{
          console.log('失敗', err);
          this.info="查詢失敗!!";
          setTimeout(() => {
           this.info = '';
           }, 2000);
        }
      })
    }

    delete(id:string){
        if(!confirm('確定要刪除嗎')) return;
        this.http.get(`http://localhost:8080/Orders/0211/delete/${id}`,{ withCredentials: true }).subscribe({
        next:()=>{
        this.info="刪除成功!!";
        setTimeout(() => {
         this.info = '';
         }, 2000);
        this.query();
        },error:(err)=>{
        console.log('刪除失敗',err);
        this.info="刪除失敗!!";
        setTimeout(() => {
         this.info = '';
         }, 2000);
        }
      });
    }

update(){}
}
