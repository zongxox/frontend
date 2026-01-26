import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';//建立表單
import { HttpClient } from '@angular/common/http';//打api
import { Router } from '@angular/router';//url
@Component({
  selector: 'app-ins0123',
  templateUrl: './ins0123.component.html',
  styleUrls: ['./ins0123.component.css']
})
export class Ins0123Component implements OnInit {

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}

  regForm!: FormGroup;
  regForm2!: FormGroup;
  info="";
  clear="";
  statusList: { statusCode: string; statusContent: string }[] = [];//單選框
  optionCodesList: { optionCodesCode: string; optionCodesContent: string }[] = [];//多選框
  eventNameList: { eventNameCode: string; eventNameContent: string }[] = [];//下拉式
  ngOnInit(): void {
    this.regForm = this.fb.group({
      memberId: ['', Validators.required],
      eventCode: ['', Validators.required],
      registerTime: ['', Validators.required],
      eventName: ['', Validators.required],
      statusCode: ['', Validators.required],
      phone: ['', Validators.required],
      email: ['', Validators.required],
      note: ['', Validators.required],
      optionCodes: [[], [this.arrayRequiredValidator()]],
      cancelTime: ['', Validators.required],
      updateTime: ['', Validators.required],
    });

 this.regForm2 = this.fb.group({
    memberId: ['', Validators.required],
    name: ['', Validators.required],
    gender: ['', Validators.required],
    phone: ['', Validators.required],
    idNumber: ['', Validators.required],
    birthDate: ['', Validators.required],
    address: ['', Validators.required],
    hobby: [''],
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

 //單選框 跟 多選框 跟下拉式 去除重複
 private uniqBy<T>(arr: T[], keyFn: (x: T) => string) : T[] {
   const map = new Map<string, T>(); //建立一個 Map
   for (const item of arr || []) { //arr || [] 是防呆：如果 arr 是 null/undefined，就用空陣列，避免錯誤
     const key = keyFn(item);//optionCodesCode 當唯一值，就會回傳它
     if (key != null && !map.has(key)) map.set(key, item);//同樣 key 的重複資料只保留第一筆
   }
   return Array.from(map.values());//Map 裡的值轉回陣列
 }

  //清除
 clearForm() {
   this.regForm.reset({
     memberId: '',
     eventCode: '',
     registerTime: '',
     eventName: '',
     statusCode: '',
     phone: '',
     email: '',
     note: '',
     optionCodes: '',
     cancelTime: '',
     updateTime: '',
   });
 this.regForm2.reset({
         memberId: '',
         name: '',
         gender:'' ,
         phone: '',
         idNumber: '',
         birthDate: '',
         address: '',
         hobby: '',
   })
     this.selectedOptionCodes=[];// 多選框陣列
     this.clear = '已清除';
   }

 // 多選框陣列
 selectedOptionCodes: string[] = [];
 // 抓取多選框
 getOptionCodes(event: Event): void {
   const target = event.target as HTMLInputElement;
   const v = target.value;
   const checked = target.checked;

   if (checked) {
     if (!this.selectedOptionCodes.includes(v)) {
       this.selectedOptionCodes.push(v);
     }
   } else {
     this.selectedOptionCodes = this.selectedOptionCodes.filter(x => x !== v);
   }
   this.regForm.get('optionCodes')?.setValue(this.selectedOptionCodes);
   this.regForm.get('optionCodes')?.markAsTouched();
   this.regForm.get('optionCodes')?.updateValueAndValidity();
   console.log(this.selectedOptionCodes);
 }
  //多選框驗證
  arrayRequiredValidator() {
      return (control: any) => {//control 就是 Angular 表單裡的一個欄位
        const v = control.value;//v 就是使用者目前輸入/選到的值
        //Array.isArray(v) 判斷v是不是陣列
        //如果 v 是陣列 而且 長度 > 0,回傳 null（代表驗證成功）
        //否則,回傳 { required: true }（代表驗證失敗，錯誤代碼是 required）
        return Array.isArray(v) && v.length > 0 ? null : { required: true };
      };
    }

//新增按鈕
insert(): void {
  const payload: any = {
    ...this.regForm.value,
    optionCodes: this.selectedOptionCodes,
    //public class EventRegistrationIns0123Req {
    //private MemberDetail0123DAO memberDetail   <class 屬性 }
    memberDetail: {//後端接收時 屬性裏面包含了 這個 類 屬性,下面是這個類裡面的屬性
      memberId: this.regForm2.value.memberId,
      name: this.regForm2.value.name,
      gender: this.regForm2.value.gender,
      phone: this.regForm2.value.phone,
      idNumber: this.regForm2.value.idNumber,
      birthDate: this.regForm2.value.birthDate,
      address: this.regForm2.value.address,
      hobby: this.regForm2.value.hobby
    }
  };
  this.http.post('http://localhost:8080/eventRegistration/0123/insert', payload).subscribe({
      next: (res: any) => {
        alert('新增成功');
        this.router.navigate(['/ind0123']);
      },
      error: (err: any) => {
        console.log('失敗', err);
        this.info = '新增失敗';
      }
    });
}




}
