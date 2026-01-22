import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';//建立表單
import { HttpClient } from '@angular/common/http';//打api
import { Router } from '@angular/router';//url
import { ActivatedRoute } from '@angular/router';//讀取目前網址資訊的工具

interface InitRow {
  statusCode: string;
  statusContent: string;
  categoryCode: string;
  categoryContent: string;
  brandCode: string;
  brandContent: string;
}


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

  //初始化下拉式選單
    this.http.get<InitRow[]>('http://localhost:8080/product/0122/initSelect').subscribe({
      next: (res: InitRow[]) => {

        // 用 Map 去重複（key=code, value=content）
        const statusMap = new Map<string, string>();
        const categoryMap = new Map<string, string>();
        const brandMap = new Map<string, string>();

        res.forEach((x: InitRow) => {
          statusMap.set(x.statusCode, x.statusContent);
          categoryMap.set(x.categoryCode, x.categoryContent);
          brandMap.set(x.brandCode, x.brandContent);
        });

        // Map → Array 丟進你的空陣列
        this.statusList = Array.from(statusMap, ([statusCode, statusContent]) => ({ statusCode, statusContent }));
        this.categoryList = Array.from(categoryMap, ([categoryCode, categoryContent]) => ({ categoryCode, categoryContent }));
        this.brandList = Array.from(brandMap, ([brandCode, brandContent]) => ({ brandCode, brandContent }));
        this.info = '初始化查詢成功';
        console.log("categoryList:", this.categoryList);

      },
    error: (err: any) => {
      console.log('失敗', err);
        this.info = '初始化查詢失敗';
      }
    });


  //利用id查詢
  this.http.post('http://localhost:8080/product/0122/updQuery',{ id:this.id}).subscribe({
    next: (res:any) => {
      console.log("res",res);
      this.updForm.patchValue(res);//將傳回來的值放入表單
      //三元運算符號
      //如果 res.zipcodes 有值（例如 "100"）
      //就走前面
      //如果 沒值（null/空的）
      //就走後面 []
      this.selectedCategory = res.category ? [String(res.category)] : [];
      this.info='查詢成功';
    },
    error: (err) => {
      console.log('查詢失敗', err);
      this.info = '查詢失敗';
    }
  });

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
