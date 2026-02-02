import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ind0130',
  templateUrl: './ind0130.component.html',
  styleUrls: ['./ind0130.component.css']
})
export class Ind0130Component implements OnInit {

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}

  initForm!: FormGroup;
  regForm!: FormGroup;
  statusList: { status: string;statusName: string;}[] = [];//單選框
  authorList: { author: string;}[] = [];//下拉式
  categoryList: { category: string;}[] = [];//多選框
  articlesList: any[] = [];//表單
  page = 1;        // 目前頁碼
  pageSize = 5;    // 每頁幾筆
  info="";
  ins="";
  ngOnInit(): void {
   this.initForm = this.fb.group({
       title:[''],
       status:[''],
       author:[''],
       category:[]
     });

      this.regForm = this.fb.group({
        title: ['',Validators.required],
        content: ['',Validators.required],
        summary: ['',Validators.required],
        author: ['',Validators.required],
        category: [],
        status: ['',Validators.required],
        views: ['',Validators.required],
        createdAt: ['',Validators.required],
        updatedAt: ['',Validators.required],
      });
   this.http.get('http://localhost:8080/Articles/0130/init',{ withCredentials: true }).subscribe({
         next:(res:any)=>{
           this.statusList = this.uniqBy(res,x=>x.status);
           this.authorList = this.uniqBy(res, x =>x.author);
           this.categoryList = this.uniqBy(res,x=>x.category);
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


  //查詢按鈕
  noData = false;
  query(){
   let value = this.initForm.value.title;
   if (!value) {
     value = '';
   }

   // 可空，但只要有值就必須是中文
   if (value !== '' && !/^[A-Za-z\u4e00-\u9fa5]+$/.test(value)) {
      this.info = '文章標題不能輸入數字';
         setTimeout(() => {
           this.info = '';
         }, 2000);
         return;
   }

const data = {
      title : this.initForm.value.title,
      author : this.initForm.value.author,
      status : this.initForm.value.status,
      category : this.selectCategory
      }

    this.http.post('http://localhost:8080/Articles/0130/query',data,{ withCredentials: true }).subscribe({
      next:(res:any)=>{
        this.articlesList = res;
        if(this.noData = res.length === 0){
          this.info="查無符合條件的資料!!";
          setTimeout(() => {
           this.info = '';
           }, 2000);
           return;
        };
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
        this.http.get(`http://localhost:8080/Articles/0130/delete/${id}`,{ withCredentials: true }).subscribe({
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


   //開關
    showAddDialog = false;
    //打開時 清除畫面
    openAddDialog() {
      this.showAddDialog = true;
      this.regForm.reset({
          title: '',
          content: '',
          summary:'' ,
          author: '',
          category:'',
          status: '',
          views: '',
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
        const title = this.regForm.value.title;
        const content = this.regForm.value.content;
        const summary = this.regForm.value.summary;
        const views = this.regForm.value.views;

        if (title && !/^[A-Za-z\u4e00-\u9fa5]+$/.test(title)) {
          this.ins = '"文章標題"不能輸入數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }

        if (content && !/^[A-Za-z\u4e00-\u9fa5]+$/.test(content)) {
          this.ins = '"文章內容"不能輸入數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }

        if (summary && !/^[A-Za-z\u4e00-\u9fa5]+$/.test(summary)) {
          this.ins = '"文章摘要"不能輸入數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }

        if (views && !/^\d+$/.test(views)) {
          this.ins = '瀏覽次數只能輸入數字,不可大於10';
          setTimeout(() => this.ins = '', 2000);
          return;
        }

        //判斷多選框有沒有被勾選
       if (this.regForm.invalid || this.selectCategory.length === 0) {
         this.regForm.markAllAsTouched(); // 顯示錯誤
         alert('請填寫完整資料');
         return;
       }

       const data = {
         ...this.regForm.value,
         category: this.selectCategory
       };
      this.http.post('http://localhost:8080/Articles/0130/insert',data,{ withCredentials: true }).subscribe({
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

          this.http.post('http://localhost:8080/Articles/0130/importExcel', formData, {  withCredentials: true,responseType: 'text' })
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

        this.http.post(`http://localhost:8080/Articles/0130/downloadExcel?excelType=${type}`,data,{withCredentials: true, responseType: 'blob' as const}).subscribe(blob => {
          const url = window.URL.createObjectURL(blob);
          const a = document.createElement('a');
          a.href = url;
          a.click();
          window.URL.revokeObjectURL(url);
        });
      }


}
