import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';


@Component({
  selector: 'app-ind0205',
  templateUrl: './ind0205.component.html',
  styleUrls: ['./ind0205.component.css']
})
export class Ind0205Component implements OnInit {

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}
  initForm!:FormGroup;
  regForm!:FormGroup;
  statusList:{status:string;statusName:string;}[]=[];//單選框
  shippingAddressList:{shippingAddress:string;}[]=[]//下拉式
  quantityList:{quantity:string;}[]=[];//多選框
  orderShipmentList:any[]=[];
  page = 1;        // 目前頁碼
  pageSize = 5;    // 每頁幾筆
  info="";
  ins="";
  ngOnInit(): void {
    this.initForm = this.fb.group({
      productName:[''],
      status:[''],
      shippingAddress:[''],
      quantity:[]
      })
   this.regForm = this.fb.group({
     orderNo: ['',Validators.required],
     customerName: ['',Validators.required],
     productName:['',Validators.required] ,
     totalPrice: ['',Validators.required],
     shippingAddress:['',Validators.required],
     status: ['',Validators.required],
     quantity: [],
     shippedAt: ['',Validators.required],
     createdAt: ['',Validators.required]
     });


    this.http.get('http://localhost:8080/OrderShipment/0205/init',{ withCredentials: true }).subscribe({
          next:(res:any)=>{
            this.statusList = this.uniqBy(res,x=>x.status);
            this.shippingAddressList = this.uniqBy(res, x =>x.shippingAddress);
            this.quantityList = this.uniqBy(res,x=>x.quantity);
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
    selectQuantity: string[] = [];

    //抓取多選框
    getQuantity(event: any): void {
      const v = event.target.value;      // 100/220/320
      const c = event.target.checked;    // true/false

      if(c){//true 或 false
          //selectedZipcodes 是一個陣列
          this.selectQuantity.push(v); //在陣列裡面加入value值
        }else{
          this.selectQuantity = this.selectQuantity.filter(x => x !== v);
        }
       console.log('目前選到的:', this.selectQuantity);
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

startEdit(orderShipment: any) {
  this.editingId = orderShipment.id;
  //把目前這一筆 course 的資料
  // 複製一份出來
  //存到 editModel 裡面，當作「編輯用的資料」。
  this.editModel = { ...orderShipment };

  //轉成陣列,讓畫面去勾選
  this.selectQuantity = orderShipment.quantity
  ? String(orderShipment.quantity).split(',').map((x: string) => x.trim())
  : [];


  console.log('進入編輯，初始化 selectLocation =', this.selectQuantity);
}


cancelEdit() {
  this.editingId = null;
  this.editModel = null;
}

//查詢按鈕
    noData = false;
    query(){
     let value = this.initForm.value.productName;
     if (!value) {
       value = '';
     }

     // 可空，但只要有值就必須是中文
     if (value !== '' && !/^[A-Za-z\u4e00-\u9fa5]+$/.test(value)) {
        this.info = '商品名稱不能輸入數字';
           setTimeout(() => {
             this.info = '';
           }, 2000);
           return;
     }

  const data = {
        productName : this.initForm.value.productName,
        shippingAddress : this.initForm.value.shippingAddress,
        status : this.initForm.value.status,
        quantity : this.selectQuantity
        }

      this.http.post('http://localhost:8080/OrderShipment/0205/query',data,{ withCredentials: true }).subscribe({
        next:(res:any)=>{
          this.orderShipmentList = res;

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
        this.http.get(`http://localhost:8080/OrderShipment/0205/delete/${id}`,{ withCredentials: true }).subscribe({
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
          orderNo: '',
          customerName: '',
          productName:'' ,
          totalPrice: '',
          shippingAddress:'',
          status: '',
          shippedAt: '',
          createdAt: '',
      });
    this.selectQuantity = [];
    }

    //關閉畫面
    closeAddDialog() {
      this.showAddDialog = false;
    }

insert(){
        const orderNo = this.regForm.value.orderNo;
        const customerName = this.regForm.value.customerName;
        const productName = this.regForm.value.productName;
        const totalPrice = this.regForm.value.totalPrice;


        if (orderNo && !/^\d{1,4}$/.test(orderNo)) {
          this.ins = '"訂單編號"只能輸入 1~4 位數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }

        if (customerName && !/^[A-Za-z\u4e00-\u9fa5]+$/.test(customerName)) {
          this.ins = '"客戶姓名"不能輸入數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }

        if (productName && !/^[A-Za-z\u4e00-\u9fa5]+$/.test(productName)) {
          this.ins = '"商品名稱"不能輸入數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }

        if (totalPrice && !/^\d{1,4}$/.test(totalPrice)) {
          this.ins = '"金額"只能輸入 1~4 位數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }


        //判斷多選框有沒有被勾選
       if (this.regForm.invalid || this.selectQuantity.length === 0) {
          this.ins="請填寫完整資料!!";
          setTimeout(() => {
           this.ins = '';
           }, 2000);
         return;
       }

       const data = {
         ...this.regForm.value,
         quantity: this.selectQuantity
       };
     console.log(data);
      this.http.post('http://localhost:8080/OrderShipment/0205/insert',data,{ withCredentials: true }).subscribe({
      next:()=>{
      this.ins="新增成功!!";
      setTimeout(() => {
       this.ins = '';
       window.location.reload();
       }, 2000);
      this.query();
      },error:(err)=>{
      console.log('新增失敗',err);
      this.ins="新增失敗!!";
      setTimeout(() => {
       this.ins = '';
       }, 2000);
      }
    });
  }

//修改按鈕
update(){
  this.editModel.quantity = this.selectQuantity;
    this.http.post('http://localhost:8080/OrderShipment/0205/update',this.editModel,{ withCredentials: true }).subscribe({
      next:(res:any)=>{
      this.cancelEdit();
      window.location.reload();
      alert("修改成功")},
    error:(err)=>{
      console.log('修改失敗',err);
      this.info="修改失敗!!";
      setTimeout(() => {this.info = '';}, 2000);
    }
  })
}

//下載
downloadExcel(): void {
  const type: 'xlsx' = 'xlsx';
  const data = {
    ...this.initForm.value,
    quantity: this.selectQuantity
  };

  this.http.post(`http://localhost:8080/OrderShipment/0205/downloadExcel?excelType=${type}`,data,{withCredentials: true, responseType: 'blob' as const}).subscribe(blob => {
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.click();
    window.URL.revokeObjectURL(url);
  });
}


// 選到的檔案
    selectedFile: File | null = null;


    // 選檔
    onFileChange(event: Event): void {
      const input = event.target as HTMLInputElement;
      if (input.files && input.files.length > 0) {
        this.selectedFile = input.files[0];
      }
    }
   // 上傳
   uploadExcel(): void {
     if (!this.selectedFile) {
       alert('請先選擇檔案');
       return;
     }

     const formData = new FormData();
     formData.append('file', this.selectedFile);

     this.http.post('http://localhost:8080/OrderShipment/0205/importExcel', formData, {  withCredentials: true,responseType: 'text' })
       .subscribe({
         next: (res: string) => {
         alert('上傳檔案成功!!');
         },
         error: (err: any) => {
         console.log('新增失敗',err);
         alert('上傳檔案成失敗!!');
         }
       });
   }
//pdf測試
downloadPdf() {
  //data 輸入框的查詢條件
  const data = {
    ...this.initForm.value,
    quantity: this.selectQuantity
  };
  this.http.post('http://localhost:8080/OrderShipment/0205/pdf',data,{responseType: 'blob',withCredentials: true}).subscribe(blob => {
    const url = window.URL.createObjectURL(blob);
    window.open(url);   // 直接開 PDF

  }, err => {
    console.error(err);
  });

}
}
