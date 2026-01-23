import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';//建立表單
import { HttpClient } from '@angular/common/http';//打api
import { Router } from '@angular/router';//url


@Component({
  selector: 'app-ins0122',
  templateUrl: './ins0122.component.html',
  styleUrls: ['./ins0122.component.css']
})
export class Ins0122Component implements OnInit {

  constructor(private fb: FormBuilder,  // fb：用來建立表單
              private http: HttpClient, // http：用來呼叫 API
              private router: Router    // router：用來跳轉頁面
              ){}

  regForm!: FormGroup;
  statusList: { statusCode: string; statusContent: string }[] = [];
  categoryList: { categoryCode: string; categoryContent: string }[] = [];
  brandList: { brandCode: string; brandContent: string }[] = [];
  info="";
  clear="";
  ngOnInit(): void {
            this.regForm = this.fb.group({
              name: ['',Validators.required],
              description: ['',Validators.required],
              price: ['',Validators.required],
              stock: ['',Validators.required],
              brand: ['',Validators.required],
              sku: ['',Validators.required],
              category: [, [this.arrayRequiredValidator()]],
              status: ['',Validators.required],
              createdTime: ['',Validators.required],
              updatedTime: ['',Validators.required],
            });



            // brand
                this.http.get('http://localhost:8080/product/0122/initSelect').subscribe({
                  next: (res: any) =>{
                 this.brandList = this.uniqBy(res, x => x.brandCode);
                  },
                  error: (err: any) => {
                    console.log('失敗', err);
                    this.info = '初始化查詢失敗';
                  }
                });

                // status
                this.http.get('http://localhost:8080/product/0122/initSelect1').subscribe({
                  next: (res: any) => {
                   this.statusList = this.uniqBy(res, x => x.statusCode);
                  },error: (err: any) => {
                    console.log('失敗', err);
                    this.info = '初始化查詢失敗';
                  }
                });

                // category
                this.http.get('http://localhost:8080/product/0122/initSelect2').subscribe({
                  next: (res: any) => {
                   this.categoryList = this.uniqBy(res, x => x.categoryCode);
                    }, error: (err: any) => {
                    console.log('失敗', err);
                    this.info = '初始化查詢失敗';
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



  //多選框驗證
  arrayRequiredValidator() {
      return (control: any) => {
        const v = control.value;
        return Array.isArray(v) && v.length > 0 ? null : { required: true };
      };
    }


  //清除方法
   clearForm() {
     this.regForm.reset({
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
      this.regForm.get('category')?.setValue(this.selectedCategory);
      this.regForm.get('category')?.markAsTouched();
      this.regForm.get('category')?.updateValueAndValidity();
     console.log(this.selectedCategory);
  }


//新增按鈕
insert(){
   this.regForm.patchValue({ category: this.selectedCategory });//將多選框塞回表單再送出
    this.http.post('http://localhost:8080/product/0122/insert',this.regForm.value).subscribe({
    next:(res:any)=>{
    alert("新增成功");
    this.router.navigate(['/ind0122']);
    },
    error:(err)=>{console.log('失敗',err);this.info='新增失敗'}
    })
  }

}
