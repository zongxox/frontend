import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-ind0126',
  templateUrl: './ind0126.component.html',
  styleUrls: ['./ind0126.component.css']
})
export class Ind0126Component implements OnInit {

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}


initForm!:FormGroup;
addForm!:FormGroup;
quantityList: { quantityCode: string; quantityContent: string }[] = [];//多選框
statusList: { statusCode: string; statusContent: string }[] = [];//單選框
productNameList: { productNameCode: string; productNameContent: string }[] = [];//下拉
OrderItemList: any[] = [];//查詢結果表單
page = 1;        // 目前頁碼
pageSize = 5;    // 每頁幾筆
  ngOnInit(): void {
            this.initForm = this.fb.group({
              status:[''],
              quantity:[''],
              itemId:[''],
              productName:['']
            });


    // 新增用
    this.addForm = this.fb.group({
      orderId: ['', Validators.required],
      itemId: ['', Validators.required],
      productName: ['', Validators.required],
      quantity: [[]],
      unitPrice: ['', Validators.required],
      discount: ['', Validators.required],
      status: ['', Validators.required],
      createdAt: ['', Validators.required],
      updatedAt: ['', Validators.required],
    });
          forkJoin({
                status: this.http.get<any[]>('http://localhost:8080/orderItem/0126/initSelect'),
                quantity: this.http.get<any[]>('http://localhost:8080/orderItem/0126/initSelect1'),
                productName: this.http.get<any[]>('http://localhost:8080/orderItem/0126/initSelect2'),
              }).subscribe({
                next: ({ status, quantity, productName }) => {
                 this.statusList = this.uniqBy(status, x => x.statusCode);
                 this.quantityList = this.uniqBy(quantity, x => x.quantityCode);
                 this.productNameList = this.uniqBy(productName, x => x.productNameCode);
                },
                error: (err) => {
                  console.log('初始化查詢失敗', err);
                   alert('初始化查詢失敗');
                }
              });

  }
        //單選框 跟 多選框 跟下拉式 去除重複
        private uniqBy<T>(arr: T[], keyFn: (x: T) => string) : T[] {
          const map = new Map<string, T>();
          for (const item of arr || []) {
            const key = keyFn(item);
            if (key != null && !map.has(key)) map.set(key, item);
          }
          return Array.from(map.values());
        }

      // 多選框陣列
      selectQuantity: string[] = [];
      // 抓取多選框
      getQuantity(event: Event): void {
        const target = event.target as HTMLInputElement;
        const v = target.value;
        const checked = target.checked;

        if (checked) {
          if (!this.selectQuantity.includes(v)) {
            this.selectQuantity.push(v);
          }
        } else {
          this.selectQuantity = this.selectQuantity.filter(x => x !== v);
        }
        console.log(this.selectQuantity);
      }

          query(): void {
            const data = {
              itemId: this.initForm.value.itemId,//輸入框
              status: this.initForm.value.status,//單選框
              quantity: this.selectQuantity,//多選框
              productName: this.initForm.value.productName,//下拉式
            };

            this.http.post('http://localhost:8080/orderItem/0126/query', data).subscribe({
              next: (res: any) => {
                this.OrderItemList = res;
              },
              error: (err: any) => {
                console.log("查詢失敗:", err);
                alert('查詢失敗');
              }
            });
          }


        delete(id: string) {
          if (!confirm('確定要刪除嗎？')) return;

          this.http.get(`http://localhost:8080/orderItem/0126/delete/${id}`).subscribe({
            next: () => {
              alert('刪除成功');
              this.query();
            },
            error: (err: any) => {
              console.log('刪除失敗', err);
              alert('刪除失敗');
            }
          });
        }


      //開關或開啟model
      showAddDialog = false;

      openAddDialog() {//開
        this.showAddDialog = true;
        this.addForm.reset({
                    orderId: '',
                    itemId: '',
                    productName: '',
                    quantity: '',
                    unitPrice: '',
                    discount: '',
                    status: '',
                    createdAt: '',
                    updatedAt: '',
                  });

                  this.selectQuantity = []; // 如果你新增用這個陣列，記得清掉
      }

      closeAddDialog() {//關
        this.showAddDialog = false;

      }

    //新增
    insert(){
     this.addForm.patchValue({ quantity: this.selectQuantity });//將多選框塞回表單再送出
     this.http.post(`http://localhost:8080/orderItem/0126/insert`,this.addForm.value).subscribe({

       next: () => {
         alert('新增成功');
         this.closeAddDialog();
         this.query();
       },
       error: (err: any) => {
         console.log('新增失敗', err);
         alert('新增失敗');
       }
     });
      }


      cloned: { [s: number]: any } = {};

      //修改
      onRowEditInit(order: any) {
        this.cloned[order.id] = { ...order };

      }

      //儲存
      onRowEditSave(order: any) {
      console.log("整筆資料:", order);
       this.http.post(`http://localhost:8080/orderItem/0126/update`,order).subscribe({
         next: () => {
           alert('修改成功');
           this.query();
         },
         error: (err: any) => {
           console.log('修改失敗', err);
           alert('修改失敗');
         }
       });

      }


      //取消
      onRowEditCancel(order: any, index: number) {
        this.OrderItemList[index] = this.cloned[order.id];
        delete this.cloned[order.id];
      }



      selectedFile: File | null = null;
      onFileChange(event: any) {
        const file = event.target.files?.[0];
        if (!file) return;
        this.selectedFile = file;
      }

      uploadExcel() {
        if (!this.selectedFile) {
           alert('請先選擇 Excel 檔案');
          return;
        }

        const formData = new FormData();
        formData.append('file', this.selectedFile);

        this.http.post('http://localhost:8080/orderItem/0126/importExcel', formData, {
          responseType: 'text'
        }).subscribe({
          next: (res) => {
           alert('上傳成功');
          },
          error: (err) => {
            console.log(err);
            alert('上傳失敗');
          }
        });
      }


    //下載
    downloadTemplate() {
      this.http.get('http://localhost:8080/orderItem/0126/downloadTemplate', {
        responseType: 'blob'
      }).subscribe(blob => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'order_item_template.xlsx';
        a.click();
        window.URL.revokeObjectURL(url);
      });
    }






}
