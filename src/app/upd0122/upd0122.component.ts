import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';//建立表單
import { HttpClient } from '@angular/common/http';//打api
import { Router } from '@angular/router';//url
import { ActivatedRoute } from '@angular/router';//讀取目前網址資訊的工具
import { forkJoin } from 'rxjs';


@Component({
  selector: 'app-upd0122',
  templateUrl: './upd0122.component.html',
  styleUrls: ['./upd0122.component.css']
})
export class Upd0122Component implements OnInit {

  constructor(private fb: FormBuilder,  // fb：用來建立表單
              private http: HttpClient, // http：用來呼叫 API
              private router: Router,    // router：用來跳轉頁面
              private route: ActivatedRoute
              ){}
    updForm!: FormGroup;
    info="";
    clear="";
    id!:string;
    statusList: { statusCode: string; statusContent: string }[] = [];
    categoryList: { categoryCode: string; categoryContent: string }[] = [];
    brandList: { brandCode: string; brandContent: string }[] = [];
  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id')!;
    this.updForm = this.fb.group({
      id:[''],
      name: [''],
      description: [''],
      price: [''],
      stock: [''],
      brand: [''],
      sku: [''],
      category: [],
      status: [''],
      createdTime: [''],
      updatedTime: [''],
    });

//   // brand
//       this.http.get('http://localhost:8080/product/0122/initSelect').subscribe({
//         next: (res: any) =>{
//         this.brandList = this.uniqBy(res, x => x.brandCode);
//         },
//         error: (err: any) => {
//           console.log('失敗', err);
//           this.info = '初始化查詢失敗';
//         }
//       });
//
//       // status
//       this.http.get('http://localhost:8080/product/0122/initSelect1').subscribe({
//         next: (res: any) => {
//          this.statusList = this.uniqBy(res, x => x.statusCode);
//         },error: (err: any) => {
//           console.log('失敗', err);
//           this.info = '初始化查詢失敗';
//         }
//       });
//
//       // category
//       this.http.get('http://localhost:8080/product/0122/initSelect2').subscribe({
//         next: (res: any) => {
//          this.categoryList = this.uniqBy(res, x => x.categoryCode);
//           }, error: (err: any) => {
//           console.log('失敗', err);
//           this.info = '初始化查詢失敗';
//         }
//       });
 forkJoin({
    brands: this.http.get<any[]>('http://localhost:8080/product/0122/initSelect'),
    statuses: this.http.get<any[]>('http://localhost:8080/product/0122/initSelect1'),
    categories: this.http.get<any[]>('http://localhost:8080/product/0122/initSelect2'),
  }).subscribe({
    next: ({ brands, statuses, categories }) => {
      this.brandList = this.uniqBy(brands, x => x.brandCode);
      this.statusList = this.uniqBy(statuses, x => x.statusCode);
      this.categoryList = this.uniqBy(categories, x => x.categoryCode);

      //清單都到齊了
      this.loadProductById();
    },
    error: (err) => {
      console.log('初始化查詢失敗', err);
      this.info = '初始化查詢失敗';
    }
  });
}

  //利用id查詢
 private loadProductById() {
   this.http.post<any>('http://localhost:8080/product/0122/updQuery', { id: this.id }).subscribe({
     next: (res) => {
       this.updForm.patchValue(res);

       //find() 回傳第一筆符合條件的資料，找不到就回傳 undefined
       //?.brandCode 如果 find 找不到（undefined）就不要報錯，直接回 undefined
       const brandCode = this.brandList.find(b => b.brandContent === res.brand)?.brandCode ?? '';
       const statusCode = this.statusList.find(s => s.statusContent === res.status)?.statusCode ?? '';
       const categoryCode = this.categoryList.find(c => c.categoryContent === res.category)?.categoryCode ?? '';

       this.updForm.patchValue({
         brand: brandCode,
         status: statusCode,
       });

       this.selectedCategory = categoryCode ? [categoryCode] : [];
       this.updForm.patchValue({ category: this.selectedCategory });

       this.info = '查詢成功';
     },
     error: (err) => {
       console.log('查詢失敗', err);
       this.info = '查詢失敗';
     }
   });
 }

private uniqBy<T>(arr: T[], keyFn: (x: T) => string) : T[] {
  const map = new Map<string, T>();
  for (const item of arr || []) {
    const key = keyFn(item);
    if (key != null && !map.has(key)) map.set(key, item);
  }
  return Array.from(map.values());
}


  //清除方法
   clearForm() {
     this.updForm.reset({
       name: '',
       description: '',
       price: '',
       stock: '',
       brand: '',
       sku: '',
       status: '',
       createdTime: '',
       updatedTime: ''
     });
       this.selectedCategory=[];
       this.clear = '已清除';
     }

  //聲明多選框陣列
  selectedCategory: string[] = [];

  //抓取多選框
  getCategory(event: any): void {
    const v = event.target.value;      // 100/220/320
    const c = event.target.checked;    // true/false

    if(c){//true 或 false
        //selectedZipcodes 是一個陣列
        this.selectedCategory.push(v); //在陣列裡面加入value值
      }else{
        this.selectedCategory = this.selectedCategory.filter(x => x !== v);
      }
     console.log(this.selectedCategory);
  }

update(){
  this.updForm.patchValue({ category: this.selectedCategory });
  this.updForm.patchValue({ id: this.id });
  console.log('this.id=', this.id);
  console.log('before patch=', this.updForm.value);
  this.http.post('http://localhost:8080/product/0122/update',this.updForm.value).subscribe({
  next:(res:any)=>{
  alert("修改成功");
  this.router.navigate(['/ind0122']);
  },
  error:(err)=>{console.log('失敗',err);this.info='新增失敗'}
  })
  }

}
