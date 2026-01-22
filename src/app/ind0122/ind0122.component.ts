import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';//建立表單
import { HttpClient } from '@angular/common/http';//打api
import { Router } from '@angular/router';//url
@Component({
  selector: 'app-ind0122',
  templateUrl: './ind0122.component.html',
  styleUrls: ['./ind0122.component.css']
})
export class Ind0122Component implements OnInit {

  constructor(    private fb: FormBuilder,  // fb：用來建立表單
                  private http: HttpClient, // http：用來呼叫 API
                  private router: Router    // router：用來跳轉頁面
              )
                  {}

  ngOnInit(): void {
  }

}
