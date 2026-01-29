import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';



@Component({
  selector: 'app-ind0129',
  templateUrl: './ind0129.component.html',
  styleUrls: ['./ind0129.component.css']
})
export class Ind0129Component implements OnInit {
  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}
  initForm!: FormGroup;
  regForm!: FormGroup;
  priceList: { price: string;}[] = [];//單選框
  originList: { origin: string;}[] = [];//下拉式
  fruitTypeList: { fruitType: string;}[] = [];//多選框
  fruitList: any[] = [];//表單
  page = 1;        // 目前頁碼
  pageSize = 5;    // 每頁幾筆
  info="";
  ngOnInit(): void {
    this.initForm = this.fb.group({
        fruitName:[''],
        price:[''],
        origin:[''],
        fruitType:[]
      });
      this.regForm = this.fb.group({
        fruitName: ['',Validators.required],
        fruitCode: ['',Validators.required],
        fruitType: [],
        price: ['',Validators.required],
        quantity: ['',Validators.required],
        origin: ['',Validators.required],
        remark: ['',Validators.required],
        creUser: ['',Validators.required],
        creDate: ['',Validators.required],
        updUser: ['',Validators.required],
        updDate: ['',Validators.required],
      });


    this.http.get('http://localhost:8080/FruitJpa/0129/init').subscribe({
      next:(res:any)=>{
        this.priceList = this.uniqBy(res,x=>x.price);
        this.originList = this.uniqBy(res, x =>x.origin);
        this.fruitTypeList = this.uniqBy(res,x=>x.fruitType);
      },error:(err:any)=>{
        console.log('失敗', err);
        alert('初始化查詢失敗');
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
  selectFruitType: string[] = [];

  //抓取多選框
  getFruitType(event: any): void {
    const v = event.target.value;      // 100/220/320
    const c = event.target.checked;    // true/false

    if(c){//true 或 false
        //selectedZipcodes 是一個陣列
        this.selectFruitType.push(v); //在陣列裡面加入value值
      }else{
        this.selectFruitType = this.selectFruitType.filter(x => x !== v);
      }
     console.log('目前選到的:', this.selectFruitType);
  }



  noData = false;
  query(){
      //const value: string = this.initForm.get('fruitName')?.value || '';
      let value = this.initForm.value.fruitName;
      if (!value) {
        value = '';
      }

      // 可空，但只要有值就必須是中文
      if (value !== '' && !/^[\u4e00-\u9fa5]+$/.test(value)) {
         this.info = '水果名稱只能輸入中文';
            setTimeout(() => {
              this.info = '';
            }, 3000);
            return;
      }

    const data = {
      fruitName : this.initForm.value.fruitName,
      price : this.initForm.value.price,
      origin : this.initForm.value.origin,
      fruitType : this.selectFruitType
      }
    this.http.post('http://localhost:8080/FruitJpa/0129/query',data).subscribe({
      next:(res:any)=>{
        this.fruitList = res;
        if(this.noData = res.length === 0){
          this.info="查無符合條件的資料!!";
          setTimeout(() => {
           this.info = '';
           }, 3000);
           return;
        };
      },error:(err:any)=>{
        console.log('失敗', err);
        alert('查詢失敗');
      }
    })
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

  delete(id:string){
      if(!confirm('確定要刪除嗎')) return;
      this.http.get(`http://localhost:8080/FruitJpa/0129/delete/${id}`).subscribe({
      next:()=>{
      alert('刪除成功');
      this.query();
      },error:(err)=>{
      console.log('刪除失敗',err);
      alert('刪除失敗');
      }
    });
  }

  //開關
  showAddDialog = false;

  openAddDialog() {
    this.showAddDialog = true;
    this.regForm.reset({
      fruitName: '',
      fruitCode: '',
      fruitType:'' ,
      price: '',
      quantity:'',
      origin: '',
      remark: '',
      creUser: '',
      creDate: '',
      updUser: '',
      updDate: '',
    });
  this.selectFruitType = [];
  }

  closeAddDialog() {
    this.showAddDialog = false;
  }


  insert(){
       if (this.regForm.invalid || this.selectFruitType.length === 0) {
         this.regForm.markAllAsTouched(); // 顯示錯誤
         alert('請填寫完整資料');
         return;
       }
       const data = {
         ...this.regForm.value,
         fruitType: this.selectFruitType
       };
     console.log(data);
      this.http.post('http://localhost:8080/FruitJpa/0129/insert',data).subscribe({
      next:()=>{
      alert('新增成功');
      window.location.reload();
      this.query();
      },error:(err)=>{
      console.log('新增失敗',err);
      alert('新增失敗');
      }
    });
  }



}
