import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HeaderComponent } from '../navigation/header/header.component';
import { AuthenticateComponent } from './authenticate.component';
import { Url } from '../http/url';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { User } from '../entities/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private router: Router, private http: HttpClient) {  }

  headerComponent: HeaderComponent;
  authenticateComponent: AuthenticateComponent;
  user: User;

  showError = false;

  ngOnInit() {
    this.headerComponent = new HeaderComponent();
    this.authenticateComponent = new AuthenticateComponent(this.router);
    this.user = {} as any;
  }

  login() {
    this.showError = false;
    const username = (<HTMLInputElement> document.getElementById('username')).value;
    const password = (<HTMLInputElement> document.getElementById('password')).value;

    this.http.get(Url.get('authentication/get/getEmployeeByUsernameAndPassword?username=' + username + '&password=' + password)).subscribe(
      (user: User) => {
        this.user = user;
        console.log(user);
        console.log(this.user);
        this.authenticateComponent.login(this.user);
      },
      (error: HttpErrorResponse) => {
        this.showError = true;
      }
    );
  }
}
