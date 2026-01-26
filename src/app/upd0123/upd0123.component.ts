import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-upd0123',
  templateUrl: './upd0123.component.html',
  styleUrls: ['./upd0123.component.css']
})
export class Upd0123Component implements OnInit {

  updForm!: FormGroup;
  updForm2!: FormGroup;

  memberId = '';
  info = '';
  clear = '';

  statusList: { statusCode: string; statusContent: string }[] = [];
  optionCodesList: { optionCodesCode: string; optionCodesContent: string }[] = [];
  eventNameList: { eventNameCode: string; eventNameContent: string }[] = [];

  selectedOptionCodes: string[] = [];

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.memberId = this.route.snapshot.paramMap.get('memberId') ?? '';
    console.log('memberId=', this.memberId);

    this.updForm = this.fb.group({
      memberId: [''],
      eventCode: [''],
      registerTime: [''],
      eventName: [''],
      statusCode: [''],
      phone: [''],
      email: [''],
      note: [''],
      optionCodes: [[], [this.arrayRequiredValidator()]],
      cancelTime: [''],
      updateTime: [''],
    });

    this.updForm2 = this.fb.group({
      memberId: [''],
      name: [''],
      gender: [''],
      phone: [''],
      idNumber: [''],
      birthDate: [''],
      address: [''],
      hobby: [''],
    });

    forkJoin({
      eventName: this.http.get<any[]>('http://localhost:8080/eventRegistration/0123/initSelect'),
      statusCode: this.http.get<any[]>('http://localhost:8080/eventRegistration/0123/initSelect1'),
      optionCodes: this.http.get<any[]>('http://localhost:8080/eventRegistration/0123/initSelect2'),
    }).subscribe({
      next: ({ eventName, statusCode, optionCodes }) => {
        this.eventNameList = this.uniqBy(eventName, (x: any) => x.eventNameCode);
        this.statusList = this.uniqBy(statusCode, (x: any) => x.statusCode);
        this.optionCodesList = this.uniqBy(optionCodes, (x: any) => x.optionCodesCode);
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
     this.http.post<any>('http://localhost:8080/eventRegistration/0123/updQuery', { memberId: this.memberId }).subscribe({
       next: (res) => {
        console.log('updQuery res=', res);

              // 1) 主表回填到 updForm
              this.updForm.patchValue({
                memberId:this.memberId,
                eventCode: res.eventCode,
                registerTime: res.registerTime,
                eventName: res.eventName,
                statusCode: res.statusCode,
                phone: res.phone,
                email: res.email,
                note: res.note,
                cancelTime: res.cancelTime,
                updateTime: res.updateTime,
              });

                 this.selectedOptionCodes = res.optionCodes ? [res.optionCodes] : [];
                 this.updForm.patchValue({ optionCodes: this.selectedOptionCodes });

              // 3) 副表 memberDetail 回填到 updForm2
              if (res.memberDetail) {
                this.updForm2.patchValue({
                  memberId:this.memberId,
                  name: res.memberDetail.name,
                  gender: res.memberDetail.gender,
                  phone: res.memberDetail.phone,
                  idNumber: res.memberDetail.idNumber,
                  birthDate: res.memberDetail.birthDate,
                  address: res.memberDetail.address,
                  hobby: res.memberDetail.hobby,
                });
              }
         this.info = '查詢成功';
       },
       error: (err) => {
         console.log('查詢失敗', err);
         this.info = '查詢失敗';
       }
     });
   }


  private uniqBy<T>(arr: T[], keyFn: (x: T) => string): T[] {
    const map = new Map<string, T>();
    for (const item of arr || []) {
      const key = keyFn(item);
      if (key != null && !map.has(key)) map.set(key, item);
    }
    return Array.from(map.values());
  }

  clearForm() {
    this.updForm.reset({
      memberId: '',
      eventCode: '',
      registerTime: '',
      eventName: '',
      statusCode: '',
      phone: '',
      email: '',
      note: '',
      optionCodes: [],
      cancelTime: '',
      updateTime: '',
    });

    this.updForm2.reset({
      memberId: '',
      name: '',
      gender: '',
      phone: '',
      idNumber: '',
      birthDate: '',
      address: '',
      hobby: '',
    });

    this.selectedOptionCodes = [];
    this.clear = '已清除';
  }

  getOptionCodes(event: Event): void {
    const target = event.target as HTMLInputElement;
    const v = target.value;
    const checked = target.checked;

    if (checked) {
      if (!this.selectedOptionCodes.includes(v)) this.selectedOptionCodes.push(v);
    } else {
      this.selectedOptionCodes = this.selectedOptionCodes.filter(x => x !== v);
    }

    this.updForm.get('optionCodes')?.setValue(this.selectedOptionCodes);
    this.updForm.get('optionCodes')?.markAsTouched();
    this.updForm.get('optionCodes')?.updateValueAndValidity();
  }

  arrayRequiredValidator() {
    return (control: any) => {
      const v = control.value;
      return Array.isArray(v) && v.length > 0 ? null : { required: true };
    };
  }

  update(){
    const payload: any = {
        ...this.updForm.value,
        optionCodes: this.selectedOptionCodes,
        memberId: this.memberId,
        memberDetail: {
          memberId: this.updForm2.value.memberId,
          name: this.updForm2.value.name,
          gender: this.updForm2.value.gender,
          phone: this.updForm2.value.phone,
          idNumber: this.updForm2.value.idNumber,
          birthDate: this.updForm2.value.birthDate,
          address: this.updForm2.value.address,
          hobby: this.updForm2.value.hobby
        }
      };
    this.http.post('http://localhost:8080/eventRegistration/0123/update',payload).subscribe({
    next:(res:any)=>{
    alert("修改成功");
    this.router.navigate(['/ind0123']);
    },
    error:(err)=>{console.log('失敗',err);this.info='新增失敗'}
    })
    }



}
