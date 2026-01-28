import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ind0128',
  templateUrl: './ind0128.component.html',
  styleUrls: ['./ind0128.component.css']
})
export class Ind0128Component implements OnInit {
  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}
  initForm!: FormGroup;
  statusList: { status: string; statusName: string;}[] = [];//單選框
  descriptionList: { description: string;}[] = [];//下拉式
  locationList: { location: string;}[] = [];//多選框
  recordsList:any[] =[];//查詢結果
  excelList:any[] =[];//查詢結果
  testList:any[] =[];//查詢結果
  page = 1;        // 目前頁碼
  pageSize = 5;    // 每頁幾筆
  ngOnInit(): void {
    this.initForm = this.fb.group ({
          title:[''],
          description:[''],
          status:[''],
          location:[]
          });

    this.http.get('http://localhost:8080/records/0128/init').subscribe({
      next: (res: any) =>{
      this.descriptionList = this.uniqBy(res, x => x.description);
      this.locationList = this.uniqBy(res, x => x.location);
      this.statusList = this.uniqBy(res, x => x.status);
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
    selectLocation: string[] = [];
    // 抓取多選框
    getLocation(event: Event): void {
      const target = event.target as HTMLInputElement;
      const v = target.value;        // categoryCode
      const checked = target.checked;

      if (checked) {
        if (!this.selectLocation.includes(v)) {
          this.selectLocation.push(v);
        }
      } else {
        this.selectLocation = this.selectLocation.filter(x => x !== v);
      }
      console.log(this.selectLocation);
    }




    query(){
        const data = {
          ...this.initForm.value,
          location: this.selectLocation
        };
        this.http.post('http://localhost:8080/records/0128/query',data).subscribe({
          next: (res: any) =>{
            this.recordsList=res;
          },
          error: (err: any) => {
            console.log('失敗', err);
            alert('表單查詢失敗');
          }
        });
      }


    //控制開關
    editingRow: any = null;

    onEdit(row: any) {
      this.editingRow = row;
    }

update(row: any) {
  const data = {
          id:row.id,
          userId:row.userId,
          title:row.title,
          description: row.description,
          startTime: row.startTime,
          endTime: row.endTime,
          status:row.status,
          location:row.location,
          createdAt:row.createdAt,
          updatedAt:row.updatedAt
    }

  this.http.post('http://localhost:8080/records/0128/update',data).subscribe({
    next: (res: any) =>{
      alert('修改成功');
    },
    error: (err: any) => {
      console.log('失敗', err);
      alert('修改失敗');
    }
  });

    this.editingRow = null;
  };


    cancel(row: any) {
      this.editingRow = null;
    }



    //讀取excel檔案內容
    selectedFile: File | null = null;

    onFileSelected(event: Event): void {
      const input = event.target as HTMLInputElement | null;
      this.selectedFile = input?.files?.[0] ?? null;
    }

    excelQuery(): void {
      if (!this.selectedFile) {
        alert('請選擇檔案');
        return;
      }

      const formData = new FormData();
      formData.append('file', this.selectedFile); // 'file' 這個 key 要和後端接收的名稱一致
      this.http.post<any[]>('http://localhost:8080/records/0128/importExcel',formData).subscribe({
        next: (data) => {
          this.excelList = data;
          console.log('excelList:', this.excelList);
          alert('讀取Excel成功');
        },
        error: (err) => {
          console.error(err);
          alert('讀取Excel失敗');
        }
      });
    }

    //新增excel畫面上的內容
    insert() {
    if (!this.excelList || this.excelList.length === 0) {
      alert('沒有可新增的資料');
    return;
    }
      this.http.post<number>('http://localhost:8080/records/0128/insert',this.excelList).subscribe({
        next: (res) => {
          alert(`成功新增 ${res} 筆`);
          window.location.reload();
        },
        error: (err) => {
          console.error(err);
          alert('新增失敗');
        }
      });
    }

    testQuery(){
            this.http.get('http://localhost:8080/records/0128/testQuery').subscribe({
              next: (res: any) =>{
                this.testList=res;
                console.log(this.testList);
              },
              error: (err: any) => {
                console.log('失敗', err);
                alert('表單查詢失敗');
              }
            });
          }
}
