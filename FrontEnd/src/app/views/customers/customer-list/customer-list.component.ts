import { Component, Input,  Output, EventEmitter, ViewEncapsulation } from '@angular/core';
import { customer } from '../models/customer';


@Component({
    selector: 'app-customer-list',    
    template: `
    <mat-list-item
    *ngFor="let customer of customers"
    layout="row"
    class="pad-xs mat-title">
    {{ customer.customerId }} - {{ customer.firstName }} {{ customer.lastName }} 
    </mat-list-item>
`,
    //encapsulation: ViewEncapsulation.Emulated,
    //styles: ['th { background-color: #04AA6D; color: white;}']
   })
   export class CustomerListComponent {
    @Input() customers?: customer[];
    @Output() selected = new EventEmitter();
   } // CustomerListComponent


 
