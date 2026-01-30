import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute,Router } from '@angular/router';

@Component({
  selector: 'app-upd0130',
  templateUrl: './upd0130.component.html',
  styleUrls: ['./upd0130.component.css']
})
export class Upd0130Component implements OnInit {
  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute
  ) {}
  id!:string;
  updForm!: FormGroup;
  statusList: { status: string;statusName: string;}[] = [];//單選框
  authorList: { author: string;}[] = [];//下拉式
  categoryList: { category: string;}[] = [];//多選框
  upd="";
  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id')!;
    this.updForm = this.fb.group({
            id:['',],
            title: [''],
            content: [''],
            summary: [''],
            author: [''],
            category: [],
            status: [''],
            views: [''],
            createdAt: [''],
            updatedAt: [''],
            createdAtStr: [''],
            updatedAtStr: [''],
          });
    this.http.get('http://localhost:8080/Articles/0130/init').subscribe({
          next:(res:any)=>{
            this.statusList = this.uniqBy(res,x=>x.status);
            this.authorList = this.uniqBy(res, x =>x.author);
            this.categoryList = this.uniqBy(res,x=>x.category);
          },error:(err:any)=>{
            console.log('失敗', err);
            alert('初始化查詢失敗');
          }
        })





    //id查詢
    this.http.get(`http://localhost:8080/Articles/0130/updQuery/${this.id}`).subscribe({
      next:(res:any)=>{
        //this.updForm.patchValue(res);
        this.updForm.patchValue({
            ...res,
            createdAt: this.date(res.createdAt),
            updatedAt: this.time(res.updatedAt),
          });
        this.selectCategory = res.category?[String (res.category)]:[];
        console.log(res);
      },error:(err:any)=>{
        console.log('查詢失敗', err);
        this.upd="查詢失敗!!";
        setTimeout(() => {
         this.upd = '';
         }, 2000);
      }
    })
  }

// "2026/01/17"
// 去掉 T，只顯示日期，把 - 改成 /
date(data: string) {
  if (!data) return '';

  // 取日期部分（支援 'T' 或 空白）
  const date = data.includes('T')
    ? data.split('T')[0]
    : data.split(' ')[0];

  return date;
}

// 去掉日期，只顯示時間
time(data: string) {
  if (!data) return '';

  // 支援 'T' 或 空白
  const time = data.includes('T')
    ? data.split('T')[1]
    : data.split(' ')[1];

  return time ?? '';
}



clearForm(){
  this.updForm.reset({
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


  //單選框 跟 多選框 跟  下拉式 去除重複
  private uniqBy<T>(arr: T[], keyFn: (x: T) => string) : T[] {
    const map = new Map<string, T>();
    for (const item of arr || []) {
      const key = keyFn(item);
      if (key != null && !map.has(key)) map.set(key, item);
    }
    return Array.from(map.values());
  }


    //修改按鈕
    update(){
              //判斷多選框有沒有被勾選
             if (this.updForm.invalid || this.selectCategory.length === 0) {
               this.updForm.markAllAsTouched(); // 顯示錯誤
               this.upd = '請填寫完整資料';
               setTimeout(() => this.upd = '', 2000);
               return;
             }

        this.updForm.patchValue({ fruitType: this.selectCategory });
        this.updForm.patchValue({ id: this.id });

        this.http.post('http://localhost:8080/Articles/0130/update',this.updForm.value).subscribe({
        next:(res:any)=>{
        this.upd="修改成功!!";
        setTimeout(() => {
         this.upd = '';
         this.router.navigate(['/ind0130']);
         }, 2000);
        },
        error:(err)=>{
          console.log('修改失敗',err);
          this.upd="修改失敗!!";
          setTimeout(() => {
           this.upd = '';
           }, 2000);
        }
      })
    }

}
