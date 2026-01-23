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
   statusList: { statusCode: string; statusContent: string }[] = [];
   optionCodesList: { optionCodesCode: string; optionCodesContent: string }[] = [];
   eventNameList: { eventNameCode: string; eventNameContent: string }[] = [];
   productList: any[] = [];
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

    query(): void {
      const data = {
        registerTime: this.initForm.value.registerTime,
        cancelTime: this.initForm.value.cancelTime,
        statusCode: this.initForm.value.statusCode,
        optionCodes: this.selectedoptionCodes,
        eventName: this.initForm.value.eventName,
      };

      this.http.post('http://localhost:8080/eventRegistration/0123/query', data).subscribe({
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



}
