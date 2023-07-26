import { Component, Injectable, Input, OnInit } from '@angular/core';
import { FormControl,FormGroup,FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AppService } from '@app/app.service';
import { User } from '@app/users/user';
import { Validator } from '@app/validators/Validator';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html'
})
@Injectable()
export class RegisterComponent implements OnInit {
    isValid = false;
    registerForm: FormGroup;
    firstname: FormControl;
    lastname: FormControl;
    email: FormControl;
    password: FormControl;
    valid = new Validator;
  constructor(private builder: FormBuilder, private app: AppService, private router: Router) 
  {
    this.firstname		= new FormControl('', Validators.compose([Validators.required]));
    this.lastname		= new FormControl('', Validators.compose([Validators.required]));
    this.password       = new FormControl('', Validators.compose([Validators.required]));
    this.email			= new FormControl('', Validators.compose([Validators.required, this.valid.ValidateEmail]));
    this.registerForm = new FormGroup({
        firstname: this.firstname,
        lastname: this.lastname,
        email: this.email,
        password: this.password
    });
   }

  ngOnInit(): void {
    
  }

  registerClicked():void 
  {

    var result: string = this.app.register({
        "firstname":this.firstname.value,
        "lastname":this.lastname.value,
        "email":this.email.value,
        "password":this.password.value,});
    this.router.navigateByUrl('/api/auth/login');   
  }
  validSwitch():void
  {
    this.isValid = !this.isValid;
  }

}
