import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { orders } from '../models/orders';
import { Observable } from 'rxjs/internal/Observable';
import { orderlineitems } from '../models/orderlineitems';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { CustomersService } from '@app/views/customers/customers.service';
import { OrdersService } from '../orders.service';
import { ProductsService } from '@app/views/products/products.service';
import { products } from '@app/views/products/models/products';
import { customers } from '@app/views/customers/models/customers';
import { map } from 'rxjs';
import { ListObjectWrapper } from '@app/ListObjectWrapper';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styles: [`
    mat-form-field {
      margin-bottom: 1.5rem;
    }

    th.mat-header-cell {
      font-weight: bold;
    }

    td.mat-cell {
      padding-top: 0.75rem;
      padding-bottom: 0.75rem;
    }
  `]
})
export class OrderDetailsComponent implements OnInit {

   CustomerforOrder: customers  = {
       customerId: 0,
       firstName: "",
       lastName: "",
       phone: "",
       email: "",
       street: "",
       city: "",
       state: "",
       zipCode: "",
       customerOrders: []
   };
    
    // customerForm: FormGroup;
    // customerId: FormControl;
    // firstName: FormControl;
    // lastName: FormControl;
    // phone: FormControl;
    // email: FormControl;
    // street: FormControl;
    // city: FormControl;
    // state: FormControl;
    // zipCode: FormControl;

    // orderForm: FormGroup;
    // orderId: FormControl;
    // orders_customerId: FormControl;
    // orderStatus: FormControl;
    // orderDate: FormControl;
    // requiredDate: FormControl;
    // shippedDate: FormControl;
    // storeId: FormControl;
    // staffId: FormControl;
    selectedCustomer: any;
    orderItems: orderlineitems[] = [];
    
    constructor(private builder: FormBuilder, public customersService: CustomersService, public orderService: OrdersService, public productService: ProductsService){}
    @Input() selectedOrder: orders = {
        orderId        : 0,
        customerId     : 0,
        orderStatus     : "",
        orderDate       : "",
        requiredDate    : "",
        shippedDate     : "",
        storeId        : 0,
        staffId        : 0,
        orderItems      : []
      };
    hideDetailsPage: boolean = false;

    myObjects$?: Observable<orderlineitems[]>;

    @Input() orders: orders[] | null = null;
    @Input() msg: string | null = null;
    @Output() cancelled = new EventEmitter();
    @Output() saved = new EventEmitter();
    @Output() deleted = new EventEmitter();
    ngOnInit(): void {

            this.customersService.getByID("customer", "/id/"+this.selectedOrder.customerId).subscribe((Customer) => {
                this.CustomerforOrder.customerId = Customer.customerId;
                this.CustomerforOrder.firstName = Customer.firstName;
                this.CustomerforOrder.lastName = Customer.lastName;
                this.CustomerforOrder.phone = Customer.phone;
                this.CustomerforOrder.email = Customer.email;
                this.CustomerforOrder.street= Customer.street;
                this.CustomerforOrder.city = Customer.city;
                this.CustomerforOrder.state = Customer.state;
                this.CustomerforOrder.zipCode = Customer.zipCode;
            });
            
              this.orderService.get<ListObjectWrapper<orderlineitems>>("orderitem", "/orderid/"+this.selectedOrder.orderId)
              .pipe(
                    map((payload: ListObjectWrapper<orderlineitems>) => {
                    return payload;
                })
            ).subscribe((data: ListObjectWrapper<orderlineitems>) =>{
                this.selectedOrder.orderItems = data.objectList;  
                data.objectList.forEach((orderItem: orderlineitems) => {
                    var res = this.productService.getProductNameByID(orderItem.productId);
                    res.pipe(
                        map((payload: any) => {
                            return payload;
                        })
                    ).subscribe((data: string) => {
                        this.productService.setProductName(orderItem.productId, data);
                    })
                })
                
              });

              

            
    }
    
    formatDate(dateString: string) {
        return `${dateString.substring(0, 4)}-${dateString.substring(4, 6)}-${dateString.substring(6, 8)}T00:00:00`;
      }
    displayedColumns: string[] = ['productId','quantity','listPrice','discount'];

    getProductName(id: number): string
    {
      var res = this.productService.getProductName(id)!;
      return res;
    }

      cancel(): void {
        this.hideDetailsPage = !this.hideDetailsPage;
      } // cancel

      calculateSubtotal(): number {
        var num: number = 0.0;
        this.selectedOrder.orderItems.forEach(x => {
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

}
