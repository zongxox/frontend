import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';


@Component({
  selector: 'app-ind0202',
  templateUrl: './ind0202.component.html',
  styleUrls: ['./ind0202.component.css']
})
export class Ind0202Component implements OnInit {

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}


    initForm!: FormGroup;
    regForm!: FormGroup;
    isPublishedList: { isPublished: string;isPublishedName: string;}[] = [];//單選框
    maxStudentsList: { maxStudents: string;}[] = [];//下拉式
    levelList: { level: string;}[] = [];//多選框
    courseList: any[] = [];//表單
    page = 1;        // 目前頁碼
    pageSize = 5;    // 每頁幾筆
    info="";
    ins="";

  ngOnInit(): void {
    this.initForm = this.fb.group({
        title:[''],
        level:[''],
        isPublished:[''],
        maxStudents:['']
      });
    this.regForm = this.fb.group({
        title:['',Validators.required],
        teacherName:['',Validators.required],
        description:['',Validators.required],
        price:['',Validators.required],
        level:[],
        maxStudents:['',Validators.required],
        isPublished:['',Validators.required],
        createdAt:['',Validators.required],
        updatedAt:['',Validators.required]
      });

   this.http.get('http://localhost:8080/Course/0202/init',{ withCredentials: true }).subscribe({
         next:(res:any)=>{
           this.isPublishedList = this.uniqBy(res,x=>x.isPublished);
           this.maxStudentsList = this.uniqBy(res, x =>x.maxStudents);
           this.levelList = this.uniqBy(res,x=>x.level);
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
    selectLevel: string[] = [];

    //抓取多選框
    getLevel(event: any): void {
      const v = event.target.value;      // 100/220/320
      const c = event.target.checked;    // true/false

      if(c){//true 或 false
          //selectedZipcodes 是一個陣列
          this.selectLevel.push(v); //在陣列裡面加入value值
        }else{
          this.selectLevel = this.selectLevel.filter(x => x !== v);
        }
       console.log('目前選到的:', this.selectLevel);
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
        isPublished : this.initForm.value.isPublished,
        maxStudents : this.initForm.value.maxStudents,
        level : this.selectLevel
        }

      this.http.post('http://localhost:8080/Course/0202/query',data,{ withCredentials: true }).subscribe({
        next:(res:any)=>{
          console.log(res);
          this.courseList = res;
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
        this.http.get(`http://localhost:8080/Course/0202/delete/${id}`,{ withCredentials: true }).subscribe({
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
          teacherName: '',
          description:'' ,
          price: '',
          maxStudents:'',
          isPublished: '',
          createdAt: '',
          updatedAt: '',
      });
    this.selectLevel = [];
    }

    //關閉畫面
    closeAddDialog() {
      this.showAddDialog = false;
    }


 insert(){
        const title = this.regForm.value.title;
        const teacherName = this.regForm.value.teacherName;
        const description = this.regForm.value.description;
        const price = this.regForm.value.price;

        if (title && !/^[A-Za-z\u4e00-\u9fa5]+$/.test(title)) {
          this.ins = '"課程名稱"不能輸入數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }

        if (teacherName && !/^[A-Za-z\u4e00-\u9fa5]+$/.test(teacherName)) {
          this.ins = '"授課老師姓名"不能輸入數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }

        if (description && !/^[A-Za-z\u4e00-\u9fa5]+$/.test(description)) {
          this.ins = '"課程簡介"不能輸入數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }

        if (price && !/^\d{1,4}$/.test(price)) {
          this.ins = '課程價格只能輸入 1~4 位數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }

        //判斷多選框有沒有被勾選
       if (this.regForm.invalid || this.selectLevel.length === 0) {
         this.regForm.markAllAsTouched(); // 顯示錯誤
          this.ins="請填寫完整資料!!";
          setTimeout(() => {
           this.ins = '';
           }, 2000);
         return;
       }

       const data = {
         ...this.regForm.value,
         level: this.selectLevel
       };
      this.http.post('http://localhost:8080/Course/0202/insert',data,{ withCredentials: true }).subscribe({
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



editingId: number | null = null;
editModel: any = null;

startEdit(course: any) {
  this.editingId = course.id;
  //把目前這一筆 course 的資料
  // 複製一份出來
  //存到 editModel 裡面，當作「編輯用的資料」。
  this.editModel = { ...course };

  // 假設 course.level 是 '初級,中級' 這種字串
  this.selectLevel = course.level
  ? String(course.level).split(',').map((x: string) => x.trim())
  : [];


  console.log('進入編輯，初始化 selectLevel =', this.selectLevel);
}


cancelEdit() {
  this.editingId = null;
  this.editModel = null;
}


    //修改按鈕
    update(){
        this.editModel.level = this.selectLevel;
        this.http.post('http://localhost:8080/Course/0202/update',this.editModel,{ withCredentials: true }).subscribe({
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

     this.http.post('http://localhost:8080/Course/0202/importExcel', formData, {  withCredentials: true,responseType: 'text' })
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
          level: this.selectLevel
        };

        this.http.post(`http://localhost:8080/Course/0202/downloadExcel?excelType=${type}`,data,{withCredentials: true, responseType: 'blob' as const}).subscribe(blob => {
          const url = window.URL.createObjectURL(blob);
          const a = document.createElement('a');
          a.href = url;
          a.click();
          window.URL.revokeObjectURL(url);
        });
      }
}
