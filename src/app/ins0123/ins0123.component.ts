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
  statusList: { statusCode: string; statusContent: string }[] = [];
  optionCodesList: { optionCodesCode: string; optionCodesContent: string }[] = [];
  eventNameList: { eventNameCode: string; eventNameContent: string }[] = [];
  ngOnInit(): void {
    this.regForm = this.fb.group({
      memberId: ['', Validators.required],
      eventCode: ['', Validators.required],
      registerTime: ['', Validators.required],
      eventName: ['', Validators.required],  // 商品品牌 (select)
      statusCode: ['', Validators.required], // 狀態(報名/取消) (radio)
      phone: ['', Validators.required], // 狀態(報名/取消) (radio)
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

 //單選框 跟 多選框
 private uniqBy<T>(arr: T[], keyFn: (x: T) => string) : T[] {
   const map = new Map<string, T>();
   for (const item of arr || []) {
     const key = keyFn(item);
     if (key != null && !map.has(key)) map.set(key, item);
   }
   return Array.from(map.values());
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
     this.selectedOptionCodes=[];
     this.clear = '已清除';
   }

 // 多選框陣列
 selectedOptionCodes: string[] = [];
 // 抓取多選框
 getOptionCodes(event: Event): void {
   const target = event.target as HTMLInputElement;
   const v = target.value;        // categoryCode
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
      return (control: any) => {
        const v = control.value;
        return Array.isArray(v) && v.length > 0 ? null : { required: true };
      };
    }

//新增按鈕
insert(): void {
  const payload: any = {
    ...this.regForm.value,
    optionCodes: this.selectedOptionCodes,
    memberDetail: {
      memberId: this.regForm.value.memberId,
      name: this.regForm.value.name,
      gender: this.regForm.value.gender,
      phone: this.regForm.value.phone,
      idNumber: this.regForm.value.idNumber,
      birthDate: this.regForm.value.birthDate,
      address: this.regForm.value.address,
      hobby: this.regForm.value.hobby
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
