import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-auth',
  template: `<div>
<app-auth-navbar></app-auth-navbar>
<router-outlet></router-outlet>
  </div>`
})
export class AuthComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
