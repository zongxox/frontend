import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ind0212',
  templateUrl: './ind0212.component.html',
  styleUrls: ['./ind0212.component.css']
})
export class Ind0212Component implements OnInit {

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}
  initForm!:FormGroup;
  statusList:{status:string;statusName:string;} [] = [];
  payMethodList:{payMethod:string;}[]=[];
  sourceList:{source:string}[]=[];
  info="";
  ins="";
  ngOnInit(): void {
    this.initForm = this.fb.group({
      receiptNo:[''],
      status:[''],
      source:[''],
      payMethod:[]
      })
    this.http.get('http://localhost:8080/PaymentRecord/0212/init',{ withCredentials: true }).subscribe({
          next:(res:any)=>{
            this.statusList = this.uniqBy(res,x=>x.status);
            this.sourceList = this.uniqBy(res, x =>x.source);
            this.payMethodList = this.uniqBy(res,x=>x.payMethod);
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
    selectPayMethod: string[] = [];

    //抓取多選框
    getPayMethod(event: any): void {
      const v = event.target.value;      // 100/220/320
      const c = event.target.checked;    // true/false

      if(c){//true 或 false
          //selectedZipcodes 是一個陣列
          this.selectPayMethod.push(v); //在陣列裡面加入value值
        }else{
          this.selectPayMethod = this.selectPayMethod.filter(x => x !== v);
        }
       console.log('目前選到的:', this.selectPayMethod);
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
}
