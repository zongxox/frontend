import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';


@Component({
  selector: 'app-ind0127',
  templateUrl: './ind0127.component.html',
  styleUrls: ['./ind0127.component.css']
})
export class Ind0127Component implements OnInit {
  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}
  initForm!: FormGroup;
  regForm!: FormGroup;
  userList:any[] =[];//查詢結果
  phoneList: { phone: string;}[] = [];//單選框
  addressList: { address: string;}[] = [];//下拉式
  zipcodesList: { zipcodes: string;}[] = [];//多選框
  page = 1;        // 目前頁碼
  pageSize = 5;    // 每頁幾筆

  ngOnInit(): void {
    this.initForm = this.fb.group ({
      name:[''],
      phone:[''],
      address:[''],
      zipcodes:[]
      });

   this.regForm = this.fb.group({
         name: ['',Validators.required],
         account: ['',Validators.required],
         password: ['',Validators.required],
         phone: ['',Validators.required],
         email: ['',Validators.required],
         address: ['',Validators.required],
         zipcodes: [[]],
         creUser: ['',Validators.required],
         creDate: ['',Validators.required],
         updUser: ['',Validators.required],
         updDate: ['',Validators.required]
       });


      this.http.get('http://localhost:8080/user/0127/init').subscribe({
        next: (res: any) =>{
        this.phoneList = this.uniqBy(res, x => x.phone);
        this.addressList = this.uniqBy(res, x => x.address);
        this.zipcodesList = this.uniqBy(res, x => x.zipcodes);
        },
        error: (err: any) => {
          console.log('失敗', err);
          alert('初始化查詢失敗');
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

  query(){
    const data = {
      ...this.initForm.value,
      zipcodes: this.selectZipcodes
    };
    this.http.post('http://localhost:8080/user/0127/query',data).subscribe({
      next: (res: any) =>{
        this.userList=res;
        console.log(res);
      },
      error: (err: any) => {
        console.log('失敗', err);
        alert('表單查詢失敗');
      }
    });

    }

    delete(id:string){
          if (!confirm('確定要刪除嗎？')) return;
          this.http.get('http://localhost:8080/user/0127/del/'+id).subscribe({
            next: (res: any) =>{
              alert('刪除成功');
              this.query();
            },
            error: (err: any) => {
              console.log('失敗', err);
              alert('刪除失敗');
            }
          });

    }
      //開關
      showAddDialog = false;

      openAddDialog() {
        this.showAddDialog = true;
        this.regForm.reset({
        name: '',
        account: '',
        password: '',
        phone: '',
        email: '',
        address: '',
        creUser: '',
        creDate: '',
        updUser: '',
        updDate: ''
      });
        this.selectZipcodes = []; // 如果你新增用這個陣列，記得清掉
      }

      closeAddDialog() {
        this.showAddDialog = false;
      }

      //新增
      insert() {
        if (this.regForm.invalid || this.selectZipcodes.length === 0) {
          this.regForm.markAllAsTouched(); // 顯示錯誤
          alert('請填寫完整資料');
          return;
        }
        const data = {
          ...this.regForm.value,
          zipcodes: this.selectZipcodes
        };
       console.log('新增送出 data =', data);
           this.http.post('http://localhost:8080/user/0127/insert',data).subscribe({
             next: (res: any) =>{
              this.closeAddDialog();
              alert('新增成功');
              window.location.reload();
             },
             error: (err: any) => {
               console.log('新增失敗', err);
               this.closeAddDialog();
               window.location.reload();
               alert('新增失敗');
             }
           });
      }


        // 選到的檔案
        selectedFile: File | null = null;


        // 選檔
        onFileChange(event: Event): void {
          const input = event.target as HTMLInputElement;
          if (input.files && input.files.length > 0) {
            this.selectedFile = input.files[0];
          }
        }

        // 上傳
        uploadExcel(): void {
          if (!this.selectedFile) {
            alert('請先選擇檔案');
            return;
          }

          const formData = new FormData();
          formData.append('file', this.selectedFile);

          this.http.post('http://localhost:8080/user/0127/importExcel', formData, { responseType: 'text' })
            .subscribe({
              next: (res: string) => {
                alert('上傳檔案成功');
              },
              error: (err: any) => {
                alert('上傳檔案失敗');
              }
            });
        }


}
