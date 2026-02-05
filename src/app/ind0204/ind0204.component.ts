import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ind0204',
  templateUrl: './ind0204.component.html',
  styleUrls: ['./ind0204.component.css']
})
export class Ind0204Component implements OnInit {

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}
  initForm!:FormGroup;
  regForm!:FormGroup;
  info="";
  ins="";
  statusList:{status : string; statusName : string; } [] = []; //單選框
  typeList:{type:string;}[]=[];//下拉式
  locationList:{location : string;}[]=[];//多選框
  taskScheduleList: any[] = [];//表格
  page = 1;        // 目前頁碼
  pageSize = 5;    // 每頁幾筆
  ngOnInit(): void {
    this.initForm = this.fb.group({
      title:[''],
      status:[''],
      type:[''],
      location:['']
      });

   this.regForm = this.fb.group({
     title: ['',Validators.required],
     status: ['',Validators.required],
     type:['',Validators.required] ,
     amount: ['',Validators.required],
     priority:['',Validators.required],
     remark: ['',Validators.required],
     location: [],
     startTime: ['',Validators.required],
     endTime: ['',Validators.required]
     });

    this.http.get('http://localhost:8080/TaskSchedule/0204/init',{ withCredentials: true }).subscribe({
          next:(res:any)=>{
            this.statusList = this.uniqBy(res,x=>x.status);
            this.typeList = this.uniqBy(res, x =>x.type);
            this.locationList = this.uniqBy(res,x=>x.location);
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
    selectLocation: string[] = [];

    //抓取多選框
    getLocation(event: any): void {
      const v = event.target.value;      // 100/220/320
      const c = event.target.checked;    // true/false

      if(c){//true 或 false
          //selectedZipcodes 是一個陣列
          this.selectLocation.push(v); //在陣列裡面加入value值
        }else{
          this.selectLocation = this.selectLocation.filter(x => x !== v);
        }
       console.log('目前選到的:', this.selectLocation);
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

startEdit(taskSchedule: any) {
  this.editingId = taskSchedule.id;
  //把目前這一筆 course 的資料
  // 複製一份出來
  //存到 editModel 裡面，當作「編輯用的資料」。
  this.editModel = { ...taskSchedule };

  // 假設 course.level 是 '初級,中級' 這種字串
  this.selectLocation = taskSchedule.location
  ? String(taskSchedule.location).split(',').map((x: string) => x.trim())
  : [];


  console.log('進入編輯，初始化 selectLocation =', this.selectLocation);
}


cancelEdit() {
  this.editingId = null;
  this.editModel = null;
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
        this.info = '活動標題不能輸入數字';
           setTimeout(() => {
             this.info = '';
           }, 2000);
           return;
     }

  const data = {
        title : this.initForm.value.title,
        type : this.initForm.value.type,
        status : this.initForm.value.status,
        location : this.selectLocation
        }

      this.http.post('http://localhost:8080/TaskSchedule/0204/query',data,{ withCredentials: true }).subscribe({
        next:(res:any)=>{
          this.taskScheduleList = res;

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
        this.http.get(`http://localhost:8080/TaskSchedule/0204/delete/${id}`,{ withCredentials: true }).subscribe({
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
          title: '',
          status: '',
          type:'' ,
          amount: '',
          priority:'',
          remark: '',
          location: '',
          startTime: '',
          endTime: '',
      });
    this.selectLocation = [];
    }

    //關閉畫面
    closeAddDialog() {
      this.showAddDialog = false;
    }

insert(){
        const title = this.regForm.value.title;
        const amount = this.regForm.value.amount;
        const priority = this.regForm.value.priority;
        const remark = this.regForm.value.remark;


        if (title && !/^[A-Za-z\u4e00-\u9fa5]+$/.test(title)) {
          this.ins = '"活動標題"不能輸入數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }

        if (priority && !/^\d{1,4}$/.test(priority)) {
          this.ins = '"優先順序"只能輸入 1~4 位數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }

        if (remark && !/^[A-Za-z\u4e00-\u9fa5]+$/.test(remark)) {
          this.ins = '"備註"不能輸入數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }

        if (amount && !/^\d{1,4}$/.test(amount)) {
          this.ins = '"金額"只能輸入 1~4 位數字';
          setTimeout(() => this.ins = '', 2000);
          return;
        }


        //判斷多選框有沒有被勾選
       if (this.regForm.invalid || this.selectLocation.length === 0) {
          this.ins="請填寫完整資料!!";
          setTimeout(() => {
           this.ins = '';
           }, 2000);
         return;
       }

       const data = {
         ...this.regForm.value,
         location: this.selectLocation
       };
     console.log(data);
      this.http.post('http://localhost:8080/TaskSchedule/0204/insert',data,{ withCredentials: true }).subscribe({
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
        this.editModel.location = this.selectLocation;
        this.http.post('http://localhost:8080/TaskSchedule/0204/update',this.editModel,{ withCredentials: true }).subscribe({
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

     this.http.post('http://localhost:8080/TaskSchedule/0204/importExcel', formData, {  withCredentials: true,responseType: 'text' })
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
    location: this.selectLocation
  };

  this.http.post(`http://localhost:8080/TaskSchedule/0204/downloadExcel?excelType=${type}`,data,{withCredentials: true, responseType: 'blob' as const}).subscribe(blob => {
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.click();
    window.URL.revokeObjectURL(url);
  });
}

//pdf測試
downloadPdf() {
  //data 輸入框的查詢條件
  const data = {
    ...this.initForm.value,
    location: this.selectLocation
  };
  this.http.post('http://localhost:8080/TaskSchedule/0204/pdf',data,{responseType: 'blob',withCredentials: true}).subscribe(blob => {
    const url = window.URL.createObjectURL(blob);
    window.open(url);   // 直接開 PDF

  }, err => {
    console.error(err);
  });

}

}
