import { FormControl,FormGroup,FormBuilder,Validators } from '@angular/forms';
import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { customers } from '../models/customers';
import { orders } from '@app/views/orders/models/orders';
import { DeleteDialogComponent } from '@app/views/delete-dialog/delete-dialog.component';
import { OrdersService } from '@app/views/orders/orders.service';
import { catchError, map, Observable } from 'rxjs';

@Component({
  selector: 'app-customer-details',
  templateUrl: './customer-details.component.html',
  styleUrls: ['./customer-details.component.css'],
})
export class CustomerDetailsComponent implements OnInit {
  @Input() selectedCustomer: customers = {
    customerId: 0,
    customerOrders: [],
    firstName: '',
    lastName: '',
    phone: '',
    email: '',
    street: '',
    city: '',
    state: '',
    zipCode: '',
  };
  //msg: string = "";
  hideOrderPage: boolean = true;
  customersOrders$?: Observable<orders[]>;
  @Input() customers: customers[] | null = null;
  @Input() msg: string | null = null;
  @Output() cancelled = new EventEmitter();
  @Output() saved = new EventEmitter();
  @Output() deleted = new EventEmitter();

  customerForm: FormGroup;
  customerId: FormControl;
  firstName: FormControl;
  lastName: FormControl;
  phone: FormControl;
  email: FormControl;
  street: FormControl;
  city: FormControl;
  state: FormControl;
  zipCode: FormControl;
  hideEditForm: boolean = true;
  constructor(private builder: FormBuilder, private dialog: MatDialog, private ordersService: OrdersService) {
    this.customerId = new FormControl(
      '',
      Validators.compose([Validators.required])
    );
    this.firstName = new FormControl(
      '',
      Validators.compose([Validators.required])
    );
    this.lastName = new FormControl(
      '',
      Validators.compose([Validators.required])
    );
    this.phone = new FormControl('', Validators.compose([Validators.required]));
    this.email = new FormControl('', Validators.compose([Validators.required]));
    this.street = new FormControl(
      '',
      Validators.compose([Validators.required])
    );
    this.city = new FormControl('', Validators.compose([Validators.required]));
    this.state = new FormControl('', Validators.compose([Validators.required]));
    this.zipCode = new FormControl(
      '',
      Validators.compose([Validators.required])
    );
    this.customerForm = new FormGroup({
      customerId: this.customerId,
      firstName: this.firstName,
      lastName: this.lastName,
      phone: this.phone,
      email: this.email,
      street: this.street,
      city: this.city,
      state: this.state,
      zipCode: this.zipCode,
    });
  }

  ngOnInit(): void {
    this.customerForm.patchValue({
      customerId:  this.selectedCustomer.customerId,
      firstName:   this.selectedCustomer.firstName,
      lastName:    this.selectedCustomer.lastName,
      phone:        this.selectedCustomer.phone,
      email:        this.selectedCustomer.email,
      street:       this.selectedCustomer.street,
      city:         this.selectedCustomer.city,
      state:        this.selectedCustomer.state,
      zipCode:     this.selectedCustomer.zipCode,
    });
  }
  ClickedOrders(): void 
  {
    //make a request to get the orders for the selected customer
    this.hideOrderPage = !this.hideOrderPage;    
  }
  cancel(msg?: string): void {
    msg ? (this.msg = 'Operation cancelled') : null;
    this.hideOrderPage = !this.hideOrderPage;
  } // cancel

  updateSelectedCustomer(): void {
    this.selectedCustomer.firstName = this.customerForm.value.firstName;
    this.selectedCustomer.lastName = this.customerForm.value.lastName;
    this.selectedCustomer.phone = this.customerForm.value.phone;
    this.selectedCustomer.email = this.customerForm.value.email;
    this.selectedCustomer.street = this.customerForm.value.street;
    this.selectedCustomer.city = this.customerForm.value.city;
    this.selectedCustomer.state = this.customerForm.value.state;
    this.selectedCustomer.zipCode = this.customerForm.value.zipCode;
    this.saved.emit(this.selectedCustomer);
  }

  openDeleteDialog(selectedCustomer: customers): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = false;
    dialogConfig.data = {
      title: `Delete Customer ${this.selectedCustomer.customerId}`,
      entityname: 'Customer',
    };
    dialogConfig.panelClass = 'customdialog';
    const dialogRef = this.dialog.open(DeleteDialogComponent, dialogConfig);
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.deleted.emit(this.selectedCustomer);
      }
    });
  } // openDeleteDialog
}
