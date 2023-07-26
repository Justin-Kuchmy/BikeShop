import { Component, Injectable, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AppService } from '@app/app.service';
import { Validator } from '@app/validators/Validator';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
})
@Injectable()
export class LoginComponent implements OnInit {

    text: string = "test string";
    loginForm: FormGroup;
    email: FormControl;
    password: FormControl;
    valid = new Validator;
    credentials = {email: '', password: ''};
  constructor(private builder: FormBuilder, private app: AppService, private router: Router) 
  {
    this.password       = new FormControl('', Validators.compose([Validators.required]));
    this.email			= new FormControl('', Validators.compose([Validators.required, this.valid.ValidateEmail]));
    this.loginForm = new FormGroup({
        email: this.email,
        password: this.password
    });
   }

  ngOnInit(): void {
  }

  loginClicked():void 
  {
    //this.setEmailVariable(this.email.value);
    //this.setPasswordVariable(this.password.value);
    var result = this.app.authenticate({"email":this.email.value,"password":this.password.value})
    .subscribe((response: string) => {
        sessionStorage.setItem('token', response);
      });;

      if (result !== null)
            sessionStorage.setItem(`email`,this.email.value);
    this.router.navigateByUrl('/api/index/home'); 
  }

  setEmailVariable(email: any):void
  {
    this.credentials.email = email;

  }
  setPasswordVariable(password: any):void
  {
    this.credentials.password = password;
  }
}
