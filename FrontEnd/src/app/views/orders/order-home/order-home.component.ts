import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { orders } from '../models/orders';
import { OrdersService } from '../orders.service';
import { MatTableDataSource } from '@angular/material/table';
import { map, catchError, Observable, firstValueFrom } from 'rxjs';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { CustomersService } from '../../customers/customers.service';
import { SearchData } from '@app/views/search/SearchData';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
//import { CustomModalComponent } from '@app/custom-modal/custom-modal.component';
import { CustomModalComponent } from '@app/custom-modal/custom-modal.component';
import { ListObjectWrapper } from '@app/ListObjectWrapper';
// import { MatDialog } from '@angular/material/dialog';
// import { Router } from '@angular/router';

@Component({
  selector: 'app-orders',
  templateUrl: './order-home.component.html',
})
export class OrderHomeComponent implements OnInit {
  msg: string;
  orders$?: Observable<orders[]>;
  orderDataSource$?: Observable<MatTableDataSource<orders>>;
  orders: orders;
  orderList!: Array<orders>;
  authorized: string[] = [];
  showNotAuthMessage: boolean = false;
  hideEditForm: boolean;
  initialLoad: boolean;
  deleteButtonClicked: boolean = false;
  deleteConfirmed: boolean = false;
  length: number;
  pageSize: number;
  searchName: String;
  searchID: number;
  obs$?: Observable<boolean>;
  customerNameOnOrder: String = '';
  displayedColumns: string[] = [
    'orderId',
    'customerId',
    'orderDate',
    'shippedDate',
    'actions',
  ];
  dataSource: MatTableDataSource<orders> = new MatTableDataSource<orders>();
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator?: MatPaginator;
  @Input() component!: Observable<Component>;

  constructor(
    public orderService: OrdersService,
    public customersService: CustomersService,
    private dialog: MatDialog,
    private router: Router,
  ) {
    this.length = 0;
    this.pageSize = 10;
    this.orders = {
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
    this.orderList;
    this.searchID = 0;
    this.searchName = '';
    this.initialLoad = true;
    this.sort = new MatSort();
    this.msg = '';
    this.hideEditForm = true;
  }

  toggleNotAuthModal(): void {
    if (this.showNotAuthMessage) {
      this.router.navigate(['/api/index/home']);
    }
    this.showNotAuthMessage = !this.showNotAuthMessage;
  }

  SearchInput(data: SearchData): void {
    if (data.Column === 'orderId')
      this.dataSource.filterPredicate = (data: orders, filter: string) =>
        !filter || data.orderId == parseInt(filter);
    if (data.Column === 'customerId')
      this.dataSource.filterPredicate = (data: orders, filter: string) =>
        !filter || data.customerId == parseInt(filter);
    this.dataSource.filter = data.Value.toString();
  }
  select(selectedorder: orders): void {
    this.orders = selectedorder;
    if(!this.deleteButtonClicked)
    {
        this.hideEditForm = !this.hideEditForm;
        this.msg = `order ${this.orders.orderId} selected`;
    }
   
  } // select


  clickedDeleteButton() {
    this.deleteButtonClicked = !this.deleteButtonClicked;
  }

  public async getAuthorization(): Promise<any> {
    const Roles$ = this.orderService.checkAuthorization();
    return firstValueFrom(Roles$);
  }

  public getOrderData()
  {
    (this.orderDataSource$ = this.orderService.get<ListObjectWrapper<orders>>("order", "/all").pipe(
      map((order: ListObjectWrapper<orders>) => {
        const dataSource = new MatTableDataSource<orders>(order.objectList);
        this.dataSource.data = order.objectList;
        this.dataSource.sort = this.sort;
        if (this.paginator !== undefined) {
          this.dataSource.paginator = this.paginator;
        }
        return dataSource;
      })
    )),
      catchError((err) => (this.msg = err.message));
  }

  ngOnInit(): void {
    var result = this.getAuthorization();
    result
      .then((data) => {
        this.authorized = data;
      })
      .catch(() => {
        this.toggleNotAuthModal();
      })
      .finally(() => {
        if (this.authorized[0] === 'ADMIN') {
          this.getOrderData();
        } else {
          this.toggleNotAuthModal();
        }
      });
  } // ngOnInit

  DateFormat(dateString: string) {
    return `${dateString.substring(0, 4)}-${dateString.substring(
      4,
      6
    )}-${dateString.substring(6, 8)}T00:00:00`;
  }

  cancel(msg?: string): void {
    msg ? (this.msg = '') : null;
    this.hideEditForm = !this.hideEditForm;
  } // cancel
}
