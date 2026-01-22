import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';//建立表單
import { HttpClient } from '@angular/common/http';//打api
import { Router } from '@angular/router';//url

@Component({
  selector: 'app-ind0121',
  templateUrl: './ind0121.component.html',
  styleUrls: ['./ind0121.component.css']
})





export class Ind0121Component implements OnInit {

  constructor(    private fb: FormBuilder,  // fb：用來建立表單
                  private http: HttpClient, // http：用來呼叫 API
                  private router: Router    // router：用來跳轉頁面
              )
                  {}
  initForm!: FormGroup;
  info = '';
  in  = '';
  //創建空陣列接收 後端的陣列 初始化下拉選單用
 authorList: { code: string; content: string }[] = [];
 statusList: { code: string; content: string }[] = [];
 categoryList: { code: string; content: string }[] = [];
  ngOnInit(): void {
        this.initForm = this.fb.group({
          title: [''], //輸入框
          status: ['0'], //單選框
          author: [''],   //作者
        });

        //初始化下拉式選單
        this.http.get('http://localhost:8080/codeType/0121/initSelect').subscribe({
          next:(res:any)=>{
            this.authorList = res.authorList;
            this.statusList = res.statusList;
            this.categoryList = res.categoryList
            this.statusList = res.statusList.map((s: any) => ({
              ...s,
              content: s.code == '1' ? '公開' : '隱藏'
            }));

            this.info='初始化查詢成功'
            },
          error:(err)=>{console.log('失敗',err);this.info='初始化查詢失敗'}
        })

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
        //v是使用者選到的值
        //filter 是過濾器
        //x => x !== v
        //x的意思是selectedZipcodes陣列裡面的[x:x:x]
        //v是畫面選到的 value
        //x = "100" → "100" !== "220" 留下
        //x = "220" → "220" !== "220" 丟掉
        //x = "320" → "320" !== "220" 留下
        this.selectedCategory = this.selectedCategory.filter(x => x !== v);
      }
     console.log(this.selectedCategory);
  }


  //聲明空陣列 接收後端傳地查詢過來的陣列物件
  postList: any[] = [];
  //點擊查詢按鈕後會執行的方法
  query(): void {
 const data = {
          title: this.initForm.value.title,
          status: this.initForm.value.status,
          author: this.initForm.value.author,
          categoryList: this.selectedCategory
        };

          this.http.post('http://localhost:8080/post/0121/query', data).subscribe({
            next: (res: any) => {
              this.postList = res;
              this.pageIndex = 0;      // 回第一頁
              this.updatePagedPosts(); // 一定要呼叫

              this.in  = "查詢成功";
            },
            error: (err) => {
              console.log("查詢失敗:", err);
              this.in  = "查詢失敗";
            }
          });
  }

  //中文轉換方法
  getAuthorName(code: string): string {
    const found = this.authorList.find((a: any) => a.code === code);
    return found ? found.content : '未知';
  }

getCategoryName(code: string): string {
  const found = this.categoryList.find((c: any) => c.code === code);
  return found ? found.content : '未知';
}

getStatusName(code: number | string): string {
  const found = this.statusList.find((s: any) => s.code === String(code));
  return found ? found.content : '未知';
}



  //刪除
    delete(id: string) {
      if (!confirm('確定要刪除嗎？')) return;

      this.http.delete(`http://localhost:8080/post/0121/delete/${id}`).subscribe({
        next: () => {
          this.in = '刪除成功';
          this.query(); //刪除後重新查詢刷新表格
        },
        error: (err) => {
          console.log('刪除失敗', err);
          this.in = '刪除失敗';
        }
      });
    }

pageIndex = 0;   // 第幾頁（0開始）
pageSize = 5;   // 每頁幾筆
pagedPosts: any[] = []; // 當頁要顯示的資料

get totalPages(): number {
  return Math.max(1, Math.ceil(this.postList.length / this.pageSize));
}

private updatePagedPosts() {
  const start = this.pageIndex * this.pageSize;
  const end = start + this.pageSize;
  this.pagedPosts = this.postList.slice(start, end);
}

nextPage() {
  if (this.pageIndex + 1 < this.totalPages) {
    this.pageIndex++;
    this.updatePagedPosts();
  }
}

prevPage() {
  if (this.pageIndex > 0) {
    this.pageIndex--;
    this.updatePagedPosts();
  }
}

changePageSize(e: Event) {
  const value = Number((e.target as HTMLSelectElement).value);
  this.pageSize = value;
  this.pageIndex = 0; // 換每頁筆數後回第一頁
  this.updatePagedPosts();
}


}
