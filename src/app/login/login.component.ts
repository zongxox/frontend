import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username = '';
  password = '';

  constructor(
    private http: HttpClient,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  login() {

    const body = new URLSearchParams();
    body.set('username', this.username);
    body.set('password', this.password);

    this.http.post('http://localhost:8080/login',body.toString(),{
        withCredentials: true,headers: new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'})
    }).subscribe({
      next: () => {
        alert('登入成功');
        this.router.navigateByUrl('/index');
      },
      error: () => {
        alert('登入失敗');
      }
    });
  }

register() {
  this.http.post('http://localhost:8080/users/0130/register',{ username: this.username, password: this.password },
    { withCredentials: true, responseType: 'text' }).subscribe({
    next: (res) => alert('註冊成功'),
    error: (err) => {
      console.log(err);
      alert('註冊失敗');
    }
  });
}




}
