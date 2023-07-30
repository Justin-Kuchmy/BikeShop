import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { orders } from '@app/views/orders/models/orders';
import { OrdersService } from '@app/views/orders/orders.service';
import { Observable, map, catchError } from 'rxjs';
import { customer } from '../models/customer';
import { ListObjectWrapper } from '@app/ListObjectWrapper';
import { orderlineitems } from '@app/views/orders/models/orderlineitems';
import { ProductsService } from '@app/views/products/products.service';

@Component({
  selector: 'app-customers-orders',
  templateUrl: './customer-orders.component.html',
  styleUrls: ['./customer-orders.component.css'],
})
export class CustomersOrdersComponent implements OnInit {
  //customersOrders$?: Observable<MatTableDataSource<orders>>;
  customersOrders$?: Observable<ListObjectWrapper<orders>>;
  ordersLineItems$?: Observable<ListObjectWrapper<orderlineitems>>;
  orders: Array<orders>;
  order: orders;
  orderProductNames!: Map<number, string>;
  msg: String = '';
  productName: String = "";
  hideEditForm: boolean;
  @Input() selectedCustomerChild: customer = {
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

  @Output() cancelled = new EventEmitter();
  @Output() saved = new EventEmitter();
  @Output() deleted = new EventEmitter();
  constructor(public ordersService: OrdersService, private productService: ProductsService) {
    this.orders = [];
    this.order = {
      orderId: 0,
      customerId: 0,
      orderStatus: '',
      orderDate: '',
      requiredDate: '',
      shippedDate: '',
      storeId: 0,
      staffId: 0,
      orderItems: [],
    };
    this.hideEditForm = true;
  }
  ngOnInit(): void {
    this.orderProductNames = new Map<number, string>();
    (this.customersOrders$ = this.ordersService.get<ListObjectWrapper<orders>>("order","/customer/items/"+this.selectedCustomerChild.customerId)),catchError((err) => (this.msg = err.message));
    this.customersOrders$.subscribe({
        // Observer object, complete method intrinscally unsubscribes
        next: (payload: ListObjectWrapper<orders>) => {
          payload.objectList.forEach((order: any) => {
            this.orders.push(order);
          });
          this.orders = payload.objectList.map((x: orders) => x);
        },
        error: (err: Error) => (this.msg = `Get failed! - ${err.message}`),
        complete: () => {},
      }); // subscribe

  } // ngOnInit

  getProductName(id: number): string
  {
    var res = this.productService.getProductName(id)!;
    return res;
  }
  select(selectedOrder: orders): void {
    this.order = selectedOrder;
    var that = this;
    (this.ordersLineItems$ = this.ordersService.get<ListObjectWrapper<orderlineitems>>("orderitem","/orderid/"+this.order.orderId)),catchError((err) => (this.msg = err.message));
    this.ordersLineItems$.subscribe({
        complete() {},
        next(payload: ListObjectWrapper<orderlineitems>) 
        {
            that.order.orderItems = payload.objectList;
            that.order.orderItems.forEach((order: orderlineitems) => {
                var res = that.productService.getProductNameByID(order.productId);
                res.pipe(
                    map((payload: any) => {
                        return payload;
                    })
                ).subscribe((data: string) => {
                    that.productService.setProductName(order.productId, data);
                })
              })
        },
        error: (err: Error) => (this.msg = `Get failed! - ${err.message}`),
    })
    this.msg = `Order ${selectedOrder.orderId} selected`;
    this.hideEditForm = !this.hideEditForm;
    
  } // select
  Back(): void 
  {
    this.msg = ``;
    this.hideEditForm = !this.hideEditForm;
  }

  
  calculateSubtotal(): number {
    var num: number = 0.0;
    this.order.orderItems.forEach(x => {
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
