import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ind0122',
  templateUrl: './ind0122.component.html',
  styleUrls: ['./ind0122.component.css']
})
export class Ind0122Component implements OnInit {

  initForm!: FormGroup;
  info = "";
  in = "";

  statusList: { statusCode: string; statusContent: string }[] = [];
  categoryList: { categoryCode: string; categoryContent: string }[] = [];
  brandList: { brandCode: string; brandContent: string }[] = [];



  // 查詢結果
  productList: any[] = [];
    page = 1;        // 目前頁碼
    pageSize = 5;    // 每頁幾筆
  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.initForm = this.fb.group({
      name: [''],
      status: [''],
      brand: [''],
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


//單選框 跟 多選框
private uniqBy<T>(arr: T[], keyFn: (x: T) => string) : T[] {
  const map = new Map<string, T>();
  for (const item of arr || []) {
    const key = keyFn(item);
    if (key != null && !map.has(key)) map.set(key, item);
  }
  return Array.from(map.values());
}


  // 多選框陣列
  selectedCategory: string[] = [];
  // 抓取多選框
  getCategory(event: Event): void {
    const target = event.target as HTMLInputElement;
    const v = target.value;        // categoryCode
    const checked = target.checked;

    if (checked) {
      if (!this.selectedCategory.includes(v)) {
        this.selectedCategory.push(v);
      }
    } else {
      this.selectedCategory = this.selectedCategory.filter(x => x !== v);
    }
    console.log(this.selectedCategory);
  }

  query(): void {
    const data = {
      name: this.initForm.value.name,
      status: this.initForm.value.status,
      category: this.selectedCategory,
      brand: this.initForm.value.brand,
    };

    this.http.post('http://localhost:8080/product/0122/query', data).subscribe({
      next: (res: any) => {
        this.productList = res;
        this.in = "查詢成功";
      },
      error: (err: any) => {
        console.log("查詢失敗:", err);
        this.in = "查詢失敗";
      }
    });
  }

  delete(id: string) {
    if (!confirm('確定要刪除嗎？')) return;

    this.http.delete(`http://localhost:8080/product/0122/delete/${id}`).subscribe({
      next: () => {
        this.in = '刪除成功';
        this.query();
      },
      error: (err: any) => {
        console.log('刪除失敗', err);
        this.in = '刪除失敗';
      }
    });
  }
}
