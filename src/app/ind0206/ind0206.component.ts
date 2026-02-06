import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ind0206',
  templateUrl: './ind0206.component.html',
  styleUrls: ['./ind0206.component.css']
})
export class Ind0206Component implements OnInit {

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}
  initForm!:FormGroup;
  regForm!:FormGroup;
  statusList:{status:string;statusName:string;}[]=[];//單選框
  categoryList:{category:string;}[]=[];  //多選框
  titleList:{title:string}[]=[];
  ticketList:any[] = [];
  info="";
  ins="";
  page = 1;        // 目前頁碼
  pageSize = 5;    // 每頁幾筆
  ngOnInit(): void {
    this.initForm = this.fb.group({
        userName:[''],
        title:[''],
        status:[''],
        category:[]
      });
    this.regForm = this.fb.group({
        userName:['',Validators.required],
        title:['',Validators.required],
        content:['',Validators.required],
        category:[],
        priority:['',Validators.required],
        status:['',Validators.required],
        contact:['',Validators.required],
        createdAt:['',Validators.required],
        updatedAt:['',Validators.required]
      });
    this.http.get('http://localhost:8080/Ticket/0206/init',{ withCredentials: true }).subscribe({
          next:(res:any)=>{
            this.statusList = this.uniqBy(res,x=>x.status);
            this.categoryList = this.uniqBy(res, x =>x.category);
            this.titleList = this.uniqBy(res,x=>x.title);
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
    selectCategory: string[] = [];

    //抓取多選框
    getCategory(event: any): void {
      const v = event.target.value;      // 100/220/320
      const c = event.target.checked;    // true/false

      if(c){//true 或 false
          //selectedZipcodes 是一個陣列
          this.selectCategory.push(v); //在陣列裡面加入value值
        }else{
          this.selectCategory = this.selectCategory.filter(x => x !== v);
        }
       console.log('目前選到的:', this.selectCategory);
    }

  //去掉T 把-改/
    approveDateStart(data: string) {
      if (!data) return '';

      // 支援 'T' 或 空白
      const [date, time] = data.includes('T')
        ? data.split('T')
        : data.split(' ');

      const newDate = date.replace(/-/g, '/');
      return `${newDate} ${time}`;
    }


//表格 開關
editingId: number | null = null;
editModel: any = null;

startEdit(ticket: any) {
  this.editingId = ticket.id;
  //把目前這一筆 course 的資料
  // 複製一份出來
  //存到 editModel 裡面，當作「編輯用的資料」。
  this.editModel = { ...ticket };

  //轉成陣列,讓畫面去勾選
  this.selectCategory = ticket.category
  ? String(ticket.category).split(',').map((x: string) => x.trim())
  : [];


  console.log('進入編輯，初始化 selectLocation =', this.selectCategory);
}


cancelEdit() {
  this.editingId = null;
  this.editModel = null;
}


//查詢按鈕
    noData = false;
    query(){
     let value = this.initForm.value.userName;
     if (!value) {
       value = '';
     }

     // 可空，但只要有值就必須是中文
     if (value !== '' && !/^[A-Za-z\u4e00-\u9fa5]+$/.test(value)) {
        this.info = '使用者名稱不能輸入數字';
           setTimeout(() => {
             this.info = '';
           }, 2000);
           return;
     }

  const data = {
        userName : this.initForm.value.userName,
        title : this.initForm.value.title,
        status : this.initForm.value.status,
        category : this.selectCategory
        }

      this.http.post('http://localhost:8080/Ticket/0206/query',data,{ withCredentials: true }).subscribe({
        next:(res:any)=>{
          this.ticketList = res;

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
        this.http.get(`http://localhost:8080/Ticket/0206/delete/${id}`,{ withCredentials: true }).subscribe({
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

//新增按鈕開關
    showAddDialog = false;
    //打開時 清除畫面
    openAddDialog() {
      this.showAddDialog = true;
      this.regForm.reset({
          userName: '',
          title: '',
          content:'' ,
          priority: '',
          status:'',
          contact: '',
          createdAt: '',
          updatedAt: '',
      });
    this.selectCategory = [];
    }

    //關閉畫面
    closeAddDialog() {
      this.showAddDialog = false;
    }

insert(){
 const orderNo = this.regForm.value.orderNo;
        const userName = this.regForm.value.userName;
        const priority = this.regForm.value.priority;
        const contact = this.regForm.value.contact;


        if (userName && !/^[A-Za-z\u4e00-\u9fa5]+$/.test(userName)) {
          this.ins = '"使用者名稱"不能輸入數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }

        if (priority && !/^\d{1,4}$/.test(priority)) {
          this.ins = '"優先順序"只能輸入 1~4 位數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }


        if (contact && !/^[A-Za-z\u4e00-\u9fa5]+$/.test(contact)) {
          this.ins = '"商品名稱"不能輸入數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }



        //判斷多選框有沒有被勾選
       if (this.regForm.invalid || this.selectCategory.length === 0) {
          this.ins="請填寫完整資料!!";
          setTimeout(() => {
           this.ins = '';
           }, 2000);
         return;
       }

}

update(){}

}
