import { Component, EventEmitter, Output, Input, OnInit } from '@angular/core';
import { customers } from '@app/views/customers/models/customers';
import { orderlineitems } from '@app/views/orders/models/orderlineitems';
import { products } from '../models/products';
import { CustomersService } from '@app/views/customers/customers.service';
import { OrdersService } from '@app/views/orders/orders.service';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { orders } from '@app/views/orders/models/orders';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { ProductsService } from '../products.service';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-product-checkout',
  templateUrl: './product-checkout.component.html',
})
export class ProductCheckoutComponent implements OnInit {

  @Output() cancelled = new EventEmitter();
  @Input() order!: products[];
  @Input() map!: Map<number, products[]>;
  newOrderID: number = 0;
  newCustomerID: number = 0;
  customerFullName: string = "";
  orderCost: number = 0.0;
  cartItems: orderlineitems[] = [];
  confirmation: boolean = false;
  newCustomer: string = "";
  userExistsAsCustomer$?: Observable<customers>
  
  customer: customers = {
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

  constructor(private productService: ProductsService, 
    private customerService: CustomersService, 
    private router: Router, 
    private orderService: OrdersService) {
    this.customerId =  new FormControl('',Validators.compose([Validators.required]));
    this.firstName =   new FormControl('',Validators.compose([Validators.required]));
    this.lastName =    new FormControl('',Validators.compose([Validators.required]));
    this.phone =        new FormControl('', Validators.compose([Validators.required]));
    this.email =        new FormControl('', Validators.compose([Validators.required]));
    this.street =       new FormControl('',Validators.compose([Validators.required]) );
    this.city =         new FormControl('', Validators.compose([Validators.required]));
    this.state =        new FormControl('', Validators.compose([Validators.required]));
    this.zipCode =     new FormControl('',Validators.compose([Validators.required]));
    this.customerForm = new FormGroup({
      customerId:  this.customerId,
      firstName:   this.firstName,
      lastName:    this.lastName,
      phone:        this.phone,
      email:        this.email,
      street:       this.street,
      city:         this.city,
      state:        this.state,
      zipCode:     this.zipCode,
    });
  }

  getProductNameFromID(productID: number): String
  {   
    var res = this.map.get(productID)![0].productName;
    return res;
    
  }

  showConfirmationModal()
  {
    this.confirmation = true;
  }

  ngOnInit(): void {

    var that = this;
    let sessionEmail = sessionStorage.getItem('email');
    if(sessionEmail !== null)
    {
        this.userExistsAsCustomer$ = this.customerService.getCustomerByEmail(sessionEmail);
       
        this.userExistsAsCustomer$.subscribe({
            complete: console.info,
            next: (details) => 
            {;
                this.customerForm.patchValue({
                    customerId:  details.customerId,
                    firstName:   details.firstName,
                    lastName:    details.lastName,
                    phone:        details.phone,
                    email:        details.email,
                    street:       details.street,
                    city:         details.city,
                    state:        details.state,
                    zipCode:     details.zipCode
                })
            },
            error(error)
            {
               if(error === 404)
               {
                that.newCustomer = 'true';
                that.productService.loadUserDetailsForCheckOut(sessionEmail!).subscribe({
                    complete: console.info,
                    next: (details) => 
                    {
                        that.customerForm.patchValue({
                            firstName:   details.firstName,
                            lastName:    details.lastName,
                            email:        details.email,
                            })
                    }
                });
               }
            }
        }); 
    }
    else
    {

        this.customerForm.patchValue({
            customerId:  this.customer.customerId,
            firstName:   this.customer.firstName,
            lastName:    this.customer.lastName,
            phone:        this.customer.phone,
            email:        this.customer.email,
            street:       this.customer.street,
            city:         this.customer.city,
            state:        this.customer.state,
            zipCode:     this.customer.zipCode
          })
    }
   

    this.map.forEach((item: products[]) => {
      const obj: orderlineitems = 
      {
        orderItemId: null,
        orderId: 1,
        itemId: 1,
        productId: item[0].productId,
        quantity: item.length,
        listPrice: item[0].listPrice,
        discount: 0.5,
      }
      this.cartItems.push(obj);
    });
  }

  onSubmit() {
    // //get the customer info from the form
    this.customer.firstName = this.customerForm.value.firstName;
    this.customer.lastName = this.customerForm.value.lastName;
    this.customer.phone = this.customerForm.value.phone;
    this.customer.email = this.customerForm.value.email;
    this.customer.street = this.customerForm.value.street;
    this.customer.city = this.customerForm.value.city;
    this.customer.state = this.customerForm.value.state;
    this.customer.zipCode = this.customerForm.value.zipCode;

    //create the new order
    var today = new Date();
    var newOrder: orders = {
      orderId        : 0,
      customerId     : this.customer.customerId,
      orderStatus     : "1",
      orderDate       : new DatePipe('en-US').transform(new Date(), 'yyyyMMdd')!.toString(),
      requiredDate    : new DatePipe('en-US').transform(new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1), 'yyyyMMdd')!.toString(),
      shippedDate     : "",
      storeId        : 0,
      staffId        : 0,
      orderItems      : this.cartItems
    };
    this.cartItems.forEach(x => this.orderCost += x.listPrice);
    this.customer.customerOrders.push(newOrder);
    //push to server. 
    this.productService.post("customer", "", this.customer).subscribe((cust: any) => 
    {
     console.log(`added: ${cust}`)
    });




    ///TODO
    var that = this;
    let sessionEmail = sessionStorage.getItem('email');

    
  }     //onsubmit

  returnToHome(showConfirmation: boolean)
  {
    this.confirmation = showConfirmation;
    this.router.navigate(['/api/index/home']);
  }
  calculateSubtotal(): number {
    var num: number = 0.0;
    this.cartItems.forEach(x => {
      num += (x.listPrice * x.quantity)
    })
    return num;
  }

  calculateTax(): number {
    const taxRate = 0.13; 
    return this.calculateSubtotal() * taxRate;
  }

  calculateGrandTotal(): number {
    return this.calculateSubtotal() + this.calculateTax();
  }
  cancel(): void{
    this.cancelled.emit('cancelled')
  }
}
