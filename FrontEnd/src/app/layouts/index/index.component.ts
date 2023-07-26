import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-index',
  template: `<div>
    <app-index-navbar></app-index-navbar>
    <router-outlet></router-outlet>
  </div>`
})
export class IndexComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
