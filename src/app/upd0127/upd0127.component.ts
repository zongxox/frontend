import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-upd0127',
  templateUrl: './upd0127.component.html',
  styleUrls: ['./upd0127.component.css']
})
export class Upd0127Component implements OnInit {

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private route:ActivatedRoute
  ) {}
  id!: string;
  updForm!: FormGroup;
  phoneList: { phone: string;}[] = [];//單選框
  addressList: { address: string;}[] = [];//下拉式
  zipcodesList: { zipcodes: string;}[] = [];//多選框
  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id')!;
    this.updForm = this.fb.group({
          id:[''],
          name: [''],
          account: [''],
          password: [''],
          phone: [''],
          email: [''],
          zipcodes:[],
          address: [''],
          creUser: [''],
          creDate: [''],
          updUser: [''],
          updDate: ['']
    });

    this.http.get('http://localhost:8080/user/0127/init').subscribe({
      next: (res: any) =>{
      this.phoneList = this.uniqBy(res, x => x.phone);
      this.addressList = this.uniqBy(res, x => x.address);
      this.zipcodesList = this.uniqBy(res, x => x.zipcodes);
      this.updQuery();
      },
      error: (err: any) => {
        console.log('失敗', err);
        alert('初始化查詢失敗');
      }
    });
  }


    //利用id查詢
     updQuery() {
       this.http.get<any>(`http://localhost:8080/user/0127/updQuery/${this.id}`).subscribe({
       next: (res) => {
        this.selectZipcodes = [String(res.zipcodes)];
        this.updForm.patchValue({
            ...res,
            zipcodes: this.selectZipcodes
          });
       },
       error: (err) => {
         console.log('查詢失敗', err);
         alert('查詢失敗');
       }
     });
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

  //清除方法
  clearForm() {
       this.updForm.reset({
            name: '',
            account: '',
            password: '',
            phone: '',
            email: '',
            address: '',
            zipcodes:[],
            creUser: '',
            creDate: '',
            updUser: '',
            updDate: ''
       });
            this.selectZipcodes = []; // 如果你新增用這個陣列，記得清掉
    }
  // 多選框陣列
  selectZipcodes: string[] = [];
  // 抓取多選框
  getZipcode(event: Event): void {
    const target = event.target as HTMLInputElement;
    const v = target.value;        // categoryCode
    const checked = target.checked;

    if (checked) {
      if (!this.selectZipcodes.includes(v)) {
        this.selectZipcodes.push(v);
      }
    } else {
      this.selectZipcodes = this.selectZipcodes.filter(x => x !== v);
    }
    console.log(this.selectZipcodes);
  }

  update(){
    this.updForm.patchValue({
    id: this.id,
    zipcodes: this.selectZipcodes
    });
    console.log('send body=', this.updForm.value);
    this.http.post('http://localhost:8080/user/0127/update',this.updForm.value).subscribe({
    next:(res:any)=>{
    alert("修改成功");
    this.router.navigate(['/ind0127']);
    },
    error:(err)=>{
      console.log('失敗',err);
      alert("新增成功");
      }
    })
    }



}
