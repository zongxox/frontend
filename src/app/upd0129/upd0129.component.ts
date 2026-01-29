import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute,Router } from '@angular/router';

@Component({
  selector: 'app-upd0129',
  templateUrl: './upd0129.component.html',
  styleUrls: ['./upd0129.component.css']
})
export class Upd0129Component implements OnInit {

  id!:string;
  updForm!: FormGroup;
  priceList: { price: string;}[] = [];//單選框
  originList: { origin: string;}[] = [];//下拉式
  fruitTypeList: { fruitType: string;}[] = [];//多選框
  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id')!;
    this.updForm = this.fb.group({
      id:[''],
      fruitName: [''],
      fruitCode: [''],
      fruitType: [],
      price: [''],
      quantity: [''],
      origin: [''],
      remark: [''],
      creUser: [''],
      creDate: [''],
      updUser: [''],
      updDate: [''],
    });
    //初始化 單選 多選  下拉式
    this.http.get('http://localhost:8080/FruitJpa/0129/init').subscribe({
      next:(res:any)=>{
        this.priceList = this.uniqBy(res,x=>x.price);
        this.originList = this.uniqBy(res, x =>x.origin);
        this.fruitTypeList = this.uniqBy(res,x=>x.fruitType);
      },error:(err:any)=>{
        console.log('失敗', err);
        alert('初始化查詢失敗');
      }
    })

    //id查詢
    this.http.get(`http://localhost:8080/FruitJpa/0129/updQuery/${this.id}`).subscribe({
      next:(res:any)=>{
        this.updForm.patchValue(res);
        this.selectFruitType = res.fruitType?[String (res.fruitType)]:[];
      },error:(err:any)=>{
        console.log('失敗', err);
        alert('查詢失敗');
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
 selectFruitType: string[] = [];
 //抓取多選框
 getFruitType(event: any): void {
   const v = event.target.value;      // 100/220/320
   const c = event.target.checked;    // true/false
   if(c){//true 或 false
     //selectedZipcodes 是一個陣列
       this.selectFruitType.push(v); //在陣列裡面加入value值
     }else{
       this.selectFruitType = this.selectFruitType.filter(x => x !== v);
     }
   console.log('目前選到的:', this.selectFruitType);
 }

clearForm(){
  this.updForm.reset({
        fruitName: '',
        fruitCode: '',
        fruitType:'' ,
        price: '',
        quantity:'',
        origin: '',
        remark: '',
        creUser: '',
        creDate: '',
        updUser: '',
        updDate: '',
      });
    this.selectFruitType = [];
    }

    //修改按鈕
    update(){
        this.updForm.patchValue({ fruitType: this.selectFruitType });
        this.updForm.patchValue({ id: this.id });
        this.http.post('http://localhost:8080/FruitJpa/0129/update',this.updForm.value).subscribe({
        next:(res:any)=>{
        alert("修改成功");
        this.router.navigate(['/ind0129']);
        },
        error:(err)=>{
          console.log('失敗',err);
          alert("修改失敗")
        }
      })
    }

}
