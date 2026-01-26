import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ind0123',
  templateUrl: './ind0123.component.html',
  styleUrls: ['./ind0123.component.css']
})
export class Ind0123Component implements OnInit {

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}
   initForm!: FormGroup;
   info="";
   in="";
   statusList: { statusCode: string; statusContent: string }[] = [];//單選框
   optionCodesList: { optionCodesCode: string; optionCodesContent: string }[] = [];//多選框
   eventNameList: { eventNameCode: string; eventNameContent: string }[] = [];//下拉式
   eventRegistrationList: any[] = [];//查詢結果表單
   page = 1;        // 目前頁碼
   pageSize = 5;    // 每頁幾筆
  ngOnInit(): void {
        this.initForm = this.fb.group({
          registerTime: [''],
          cancelTime:[''],
          eventName: [''],
          statusCode:['']
        });
      // eventNameList
      this.http.get('http://localhost:8080/eventRegistration/0123/initSelect').subscribe({
        next: (res: any) =>{
       this.eventNameList = this.uniqBy(res, x => x.eventNameCode);
        },
        error: (err: any) => {
          console.log('失敗', err);
          this.info = '初始化查詢失敗';
        }
      });

     // status
     this.http.get('http://localhost:8080/eventRegistration/0123/initSelect1').subscribe({
       next: (res: any) => {
        this.statusList = this.uniqBy(res, x => x.statusCode);
       },error: (err: any) => {
         console.log('失敗', err);
         this.info = '初始化查詢失敗';
       }
     });

      // optionCodes
        this.http.get('http://localhost:8080/eventRegistration/0123/initSelect2').subscribe({
          next: (res: any) => {
           this.optionCodesList = this.uniqBy(res, x => x.optionCodesCode);
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
  selectedoptionCodes: string[] = [];
  // 抓取多選框
  getoptionCodes(event: Event): void {
    const target = event.target as HTMLInputElement;
    const v = target.value;        // categoryCode
    const checked = target.checked;

    if (checked) {
      if (!this.selectedoptionCodes.includes(v)) {
        this.selectedoptionCodes.push(v);
      }
    } else {
      this.selectedoptionCodes = this.selectedoptionCodes.filter(x => x !== v);
    }
    console.log(this.selectedoptionCodes);
  }

    //查詢按鈕
    query(): void {
      const data = {
        registerTime: this.initForm.value.registerTime,//輸入框
        cancelTime: this.initForm.value.cancelTime,//輸入框
        statusCode: this.initForm.value.statusCode,//單選框
        optionCodes: this.selectedoptionCodes,//多選框
        eventName: this.initForm.value.eventName,//下拉式
      };

      this.http.post('http://localhost:8080/eventRegistration/0123/query', data).subscribe({
        next: (res: any) => {
          this.eventRegistrationList = res;
          this.in = "查詢成功";
        },
        error: (err: any) => {
          console.log("查詢失敗:", err);
          this.in = "查詢失敗";
        }
      });
    }

  delete(memberId: string) {
    if (!confirm('確定要刪除嗎？')) return;

    this.http.delete(`http://localhost:8080/eventRegistration/0123/delete/${memberId}`).subscribe({
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
//下載檔案
      don(memberId: string) {
        this.http.get(`http://localhost:8080/eventRegistration/0123/download/${memberId}`, {
          observe: 'response',
          // 告訴 Angular：這次回來的不是 JSON，而是「檔案資料」(二進位)
          // 如果不寫 blob，Angular 會當 JSON 解析，通常就會失敗跑到 error
          responseType: 'blob'
        }).subscribe({
          next: (resp) => {

            const cd = resp.headers.get('Content-Disposition') || '';
            let filename = '';

            const matchStar = cd.match(/filename\*\=UTF-8''([^;]+)/i);
            const match = cd.match(/filename=\"?([^\";]+)\"?/i);

            if (matchStar?.[1]) filename = decodeURIComponent(matchStar[1]);
            else if (match?.[1]) filename = match[1];

            //取出後端回傳的檔案本體 (Blob = 一包二進位檔案資料，例如 zip/pdf/jpg)
            const blob = resp.body!;

            //把這包 Blob 轉成瀏覽器可以使用的「暫時網址」(存在記憶體裡的 URL)
            //例如會變成 blob:http://localhost:4200/xxxxxxx...
            const url = window.URL.createObjectURL(blob);

            //動態建立一個 <a> 超連結標籤（但不會顯示在畫面上）
            const a = document.createElement('a');

            //把超連結的 href 指向剛剛產生的 blob URL
            //等於按下去就會抓這包 Blob 內容
            a.href = url;

            a.download = filename;

            //模擬使用者點擊這個連結
            //這一步會觸發瀏覽器的下載動作（或開啟檔案，取決於瀏覽器設定）
            a.click();

            //釋放剛剛建立的 blob URL（避免記憶體一直累積佔用）
            window.URL.revokeObjectURL(url);
          },
        error: async (err: any) => {
          console.log('下載 error 物件=', err);
          }
        });
      }


    //存放「目前選到的檔案」；一開始沒有選檔所以是 null
    selectedFile: File | null = null;

    // 當 <input type="file"> 選檔變更時會觸發（通常綁 (change)="onFileChange($event)"）
    onFileChange(event: any) {
      //event.target 是那個 file input；files[0] 是使用者選到的第一個檔案
      this.selectedFile = event.target.files[0];
    }

    //上傳方法：帶入 memberId（你用它組 API URL）
    upload(memberId: string) {

      //如果還沒選檔案，就提示並中止
      if (!this.selectedFile) {
        alert("請先選擇檔案");  //提示使用者要先選檔
        return;                 //結束方法，不繼續往下送 request
      }

      //建立 FormData（multipart/form-data），用來包「檔案 + 其他欄位」
      const formData = new FormData();

      //把檔案放進 formData；key 叫 "file"
      // 後端通常用 @RequestParam("file") MultipartFile file 來接
      formData.append("file", this.selectedFile);

      //發送 POST 上傳請求：URL 帶 memberId；body 傳 formData（含檔案）
      //request body：FormData（含檔案）
      //responseType: 'text' 後端回傳純文字（不是 JSON）
      this.http.post(`http://localhost:8080/eventRegistration/0123/upload/${memberId}`,formData,{responseType: 'text'}).subscribe({
        next: (res) => {
          alert(res); //顯示後端回傳文字，例如「上傳成功」
        },

        //失敗回應會走這裡（HTTP 4xx/5xx 或網路錯誤）
        error: (err) => {
          console.log(err);
          alert("上傳失敗");
        }
      });
    }





}
