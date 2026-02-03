import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';


@Component({
  selector: 'app-ind0203',
  templateUrl: './ind0203.component.html',
  styleUrls: ['./ind0203.component.css']
})
export class Ind0203Component implements OnInit {

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}
  initForm!: FormGroup;
  regForm!: FormGroup;
  statusList: { status: string;statusName: string;}[] = [];//單選框
  brandList: { brand: string;}[] = [];//下拉式
  categoryList: { category: string;}[] = [];//多選框
  productList: any[] = [];//表單
  page = 1;        // 目前頁碼
  pageSize = 5;    // 每頁幾筆
  info="";
  ins="";
  ngOnInit(): void {
      this.initForm = this.fb.group({
          name:[''],
          brand:[''],
          status:[''],
          category:[]
        });
    this.regForm = this.fb.group({
        name:['',Validators.required],
        description:['',Validators.required],
        price:['',Validators.required],
        stock:['',Validators.required],
        category:[],
        brand:['',Validators.required],
        sku:['',Validators.required],
        status:['',Validators.required],
        createdTime:['',Validators.required],
        updatedTime:['',Validators.required]
      });
    this.http.get('http://localhost:8080/Product/0203/init',{ withCredentials: true }).subscribe({
          next:(res:any)=>{
            this.statusList = this.uniqBy(res,x=>x.status);
            this.brandList = this.uniqBy(res, x =>x.brand);
            this.categoryList = this.uniqBy(res,x=>x.category);
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

editingId: number | null = null;
editModel: any = null;

startEdit(product: any) {
  this.editingId = product.id;
  //把目前這一筆 course 的資料
  // 複製一份出來
  //存到 editModel 裡面，當作「編輯用的資料」。
  this.editModel = { ...product };

  // 假設 course.level 是 '初級,中級' 這種字串
  this.selectCategory = product.category
  ? String(product.category).split(',').map((x: string) => x.trim())
  : [];


  console.log('進入編輯，初始化 selectCategory =', this.selectCategory);
}


cancelEdit() {
  this.editingId = null;
  this.editModel = null;
}


    //查詢按鈕
    noData = false;
    query(){
     let value = this.initForm.value.name;
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
        name : this.initForm.value.name,
        brand : this.initForm.value.brand,
        status : this.initForm.value.status,
        category : this.selectCategory
        }

      this.http.post('http://localhost:8080/Product/0203/query',data,{ withCredentials: true }).subscribe({
        next:(res:any)=>{
          this.productList = res;

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
        this.http.get(`http://localhost:8080/Product/0203/delete/${id}`,{ withCredentials: true }).subscribe({
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
          name: '',
          description: '',
          price:'' ,
          stock: '',
          brand:'',
          sku: '',
          status: '',
          createdTime: '',
          updatedTime: '',
      });
    this.selectCategory = [];
    }

    //關閉畫面
    closeAddDialog() {
      this.showAddDialog = false;
    }


 insert(){
        const name = this.regForm.value.name;
        const stock = this.regForm.value.stock;
        const description = this.regForm.value.description;
        const price = this.regForm.value.price;
        const sku = this.regForm.value.sku;


        if (name && !/^[A-Za-z\u4e00-\u9fa5]+$/.test(name)) {
          this.ins = '"商品名稱"不能輸入數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }

        if (description && !/^[A-Za-z\u4e00-\u9fa5]+$/.test(description)) {
          this.ins = '"商品描述"不能輸入數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }

        if (price && !/^\d{1,4}$/.test(price)) {
          this.ins = '商品價格只能輸入 1~4 位數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }

        if (stock && !/^\d{1,4}$/.test(stock)) {
          this.ins = '庫存只能輸入 1~4 位數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }

        if (sku && !/^\d{1,4}$/.test(sku)) {
          this.ins = '商品編號只能輸入 1~4 位數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }

        //判斷多選框有沒有被勾選
       if (this.regForm.invalid || this.selectCategory.length === 0) {
         //this.regForm.markAllAsTouched(); // 顯示錯誤
          this.ins="請填寫完整資料!!";
          setTimeout(() => {
           this.ins = '';
           }, 2000);
         return;
       }

       const data = {
         ...this.regForm.value,
         category: this.selectCategory
       };
     console.log(data);
      this.http.post('http://localhost:8080/Product/0203/insert',data,{ withCredentials: true }).subscribe({
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
        this.editModel.category = this.selectCategory;
        this.http.post('http://localhost:8080/Product/0203/update',this.editModel,{ withCredentials: true }).subscribe({
        next:(res:any)=>{
        this.cancelEdit();
        window.location.reload();
        alert("修改成功")
        },
        error:(err)=>{
          console.log('修改失敗',err);
          this.info="修改失敗!!";
          setTimeout(() => {
           this.info = '';
           }, 2000);
        }
      })
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

     this.http.post('http://localhost:8080/Product/0203/importExcel', formData, {  withCredentials: true,responseType: 'text' })
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

       //下載
       downloadExcel(): void {
         const type: 'xlsx' = 'xlsx';
         const data = {
           ...this.initForm.value,
           category: this.selectCategory
         };

         this.http.post(`http://localhost:8080/Product/0203/downloadExcel?excelType=${type}`,data,{withCredentials: true, responseType: 'blob' as const}).subscribe(blob => {
           const url = window.URL.createObjectURL(blob);
           const a = document.createElement('a');
           a.href = url;
           a.click();
           window.URL.revokeObjectURL(url);
         });
       }


// SMR 清單
smrRows: any[] = [];
smrInfo = '';

querySmrA16() {
  const body = {
    companyId: "",
    evalDate: "20251231",
    pagingSet: { start: 0, pageSize: 10 }
  };

  this.http.post<any>('http://localhost:8080/Product/0203/smr',body).subscribe({
    next: (res) => {
        //資料回傳過來的結構 res>data>result>pages
        const pages: any[] = res?.data?.result?.pages ?? [];

        this.smrRows = pages.slice().sort((a: any, b: any) => {

          //1.先用「公司名稱」排序（由大到小）
          //zh-TW 繁體中文
          //由大到小 b>a  由小到大 a > b
          const nameCompare = String(b.companyName ?? '').localeCompare(String(a.companyName ?? ''),'zh-TW');

          // 2.如果公司名稱不一樣，直接用公司名稱的結果決定順序
          // 不需要再用狀態比較
          if (nameCompare !== 0) {
            return nameCompare;
          }

          //3.公司名稱一樣，再用「狀態」排序（由大到小）
          return String(b.status ?? '').localeCompare(String(a.status ?? ''), 'zh-TW');
        });
    },
    error: (err) => {
      console.log('ERR', err);
    }
  });
}

}
