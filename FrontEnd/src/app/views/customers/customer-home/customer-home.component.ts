import { Component, OnInit, ViewChild } from '@angular/core';
import { Observable, lastValueFrom, firstValueFrom, of } from 'rxjs';
import { MatPaginator } from '@angular/material/paginator';
import { map, catchError, switchMap, filter, take } from 'rxjs/operators';
import { CustomersService } from '../customers.service';
import { SearchFilterPipe } from './SearchFilterPipe';
import { customer } from '../models/customer';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { CustomModalComponent } from '@app/custom-modal/custom-modal.component';
import { ListObjectWrapper } from '@app/ListObjectWrapper';
// import { CustomModalComponent } from '@app/custom-modal/custom-modal.component';
// import { Router } from '@angular/router';

@Component({
  selector: 'app-customers',
  templateUrl: './customer-home.component.html',
  styleUrls: ['./customer-home.component.css'],
})
export class CustomerHomeComponent implements OnInit {
  msg: string;
  customers$?: Observable<customer[]>;
  obs$?: Observable<string>;
  authorized: string[] = [];
  customerDataSource$?: Observable<MatTableDataSource<customer>>;
  customers: customer;
  customerList = [];
  showNotAuthMessage: boolean = false;
  hideEditForm: boolean;
  initialLoad: boolean;

  //sorting
  size: number = 0;
  displayedColumns: string[] = ['id', 'firstname', 'lastname'];
  dataSource: MatTableDataSource<customer> =
    new MatTableDataSource<customer>();
  length: number;
  pageSize: number;
  searchName: String;
  searchID: number;

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator?: MatPaginator;

  get SearchName() {
    return this.searchName;
  }
  set SearchName(name: String) {
    this.searchName = name;
  }
  get SearchID() {
    return this.searchID;
  }
  set SearchID(id: number) {
    this.searchID = id;
  }

  constructor(
    public customersService: CustomersService,
    public searchFilterPipe: SearchFilterPipe,
    private dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.length = 0;
    this.pageSize = 10;
    this.customers = {
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
    this.customerList;
    this.searchID = 0;
    this.searchName = '';
    this.initialLoad = true;
    this.sort = new MatSort();
    this.msg = '';
    this.hideEditForm = true;
  }


  public async getAuthorization(): Promise<any>
  {
    const Roles$ = this.customersService.checkAuthorization();
    return firstValueFrom(Roles$);

  }

  public getCustomerData()
  {
     (this.customerDataSource$ = this.customersService.get<ListObjectWrapper<customer>>("customer", "/all").pipe(
      map((customer: ListObjectWrapper<customer>) => {
        const dataSource = new MatTableDataSource<customer>(customer.objectList);
        this.dataSource.data = customer.objectList;
        this.dataSource.sort = this.sort;
        if (this.paginator !== undefined) {
          this.dataSource.paginator = this.paginator;
        }
        return dataSource;
      })
    )),
      catchError((err) => (this.msg = err.message));
  }

  toggleNotAuthModal(): void {
    if(this.showNotAuthMessage) 
    {
        this.router.navigate(['/api/index/home']);
    }
    this.showNotAuthMessage = !this.showNotAuthMessage;
}

  ngOnInit(): void {
    var result = this.getAuthorization();
    result.then((data) => {
        this.authorized = data;
      })
      .catch(() => {
        this.toggleNotAuthModal();
      })
      .finally(() => {
        if(this.authorized[0] === "ADMIN") {
            this.getCustomerData();
        }
        else
        {
             this.toggleNotAuthModal();
         }
      });
  } // ngOnInit

  select(selectedcustomer: customer): void {
    this.customers = selectedcustomer;
    this.msg = `customer ${selectedcustomer.customerId} selected`;
    this.hideEditForm = !this.hideEditForm;
  } // select

  /**
   * cancelled - event handler for cancel button
   */
  cancel(msg?: string): void {
    msg ? (this.msg = 'Operation cancelled') : null;
    this.hideEditForm = !this.hideEditForm;
  } // cancel

  update(Customers: customer): void {
    // this.customersService.update(Customers).subscribe({
    //   // Create observer object
    //   next: (emp: customers) => (this.msg = `Customer ${emp} updated!`),
    //   error: (err: Error) => (this.msg = `Update failed! - ${err.message}`),
    //   complete: () => (this.hideEditForm = !this.hideEditForm),
    // });
    this.customersService.put<ListObjectWrapper<customer>>("customer","/edit",Customers).subscribe({
      // Create observer object
      next: (emp: ListObjectWrapper<customer>) => {(this.msg = `Customer ${emp} updated!`)},
      complete: () => (this.hideEditForm = !this.hideEditForm),
      error: (err: Error) => (this.msg = `Update failed! - ${err.message}`),
    });
  } // update
  /**
   * save - event handler for saved event
   * purpose: check whether we're doing an add or update by seeing
   * if there is an emp id (update) or not (add)
   */
  save(Customers: customer): void {
    Customers.customerId ? this.update(Customers) : this.add(Customers);
  } // save

  /**
   * add - event handler for click event.
   * Passes new emp data to service and subscribes to services's http post
   * to get the returend Customer.
   */
  add(Customer: customer): void {
    // Customer.customerId = 0;
    // this.customersService.add(Customer).subscribe({
    //   //create observer object
    //   next: (emp: customers) => {
    //     this.msg = `customer ${emp.customerId} added!`;
    //   },
    //   error: (err: Error) =>
    //     (this.msg = `customer not added! - ${err.message}`),
    //   complete: () => (this.hideEditForm = !this.hideEditForm), //auto hide form after complete
    // });
  } // add

  /**
   * delete - event handler for the delete event.
   * passed Customers id to the service and subs to the services https
   * delete to get the value returned which is number of the rows deleted
   */
  delete(customer: customer): void {
    // this.customersService.delete(customer.customerId).subscribe({
    //   // Create observer object
    //   next: (numOfCustomerssDeleted: number) => {
    //     numOfCustomerssDeleted === 1
    //       ? (this.msg = `Customers ${customer.firstName} ${customer.lastName} deleted!`)
    //       : (this.msg = `Customers not deleted`);
    //   },
    //   error: (err: Error) => (this.msg = `Delete failed! - ${err.message}`),
    //   complete: () => (this.hideEditForm = !this.hideEditForm),
    // });
    this.customersService.delete<number>("customer", "delete/"+customer.customerId).subscribe({
      // Create observer object
      next: (numOfCustomerssDeleted: number) => {
        numOfCustomerssDeleted === 1
          ? (this.msg = `Customers ${customer.firstName} ${customer.lastName} deleted!`)
          : (this.msg = `Customers not deleted`);
      },
      error: (err: Error) => (this.msg = `Delete failed! - ${err.message}`),
      complete: () => (this.hideEditForm = !this.hideEditForm),
    });
  } // delete

  /**
   * newCustomers - create new Customers instance
   */
  newCustomers(): void {
    this.customers = {
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
    this.hideEditForm = !this.hideEditForm;
    this.msg = 'New Customer';
  } // newCustomers

  searchByName() {
    this.dataSourcePipe('name', this.searchName);
  }

  dataSourcePipe(prop: String, value: String): void {
    if (value.length === 0) {
      this.ngOnInit();
    } else {
      (this.customerDataSource$ = this.searchFilterPipe
        .getByString(prop, value)
        .pipe(
          map((customer: any) => {
            const dataSource = new MatTableDataSource<customer>(customer);
            this.dataSource.data = customer;
            this.dataSource.sort = this.sort;
            if (this.paginator !== undefined) {
              this.dataSource.paginator = this.paginator;
            }
            return dataSource;
          })
        )),
        catchError((err) => (this.msg = err.message));
    }
  }
  SortProductsWithObjectLiterals(sort: Sort): void {
    const literals = {
      id: () =>
        (this.dataSource.data = this.dataSource.data.sort(
          (a: customer, b: customer) =>
            sort.direction === 'asc'
              ? a.customerId < b.customerId
                ? -1
                : 1
              : b.customerId < a.customerId // descending
              ? -1
              : 1 // descending
        )),
      firstname: () =>
        (this.dataSource.data = this.dataSource.data.sort(
          (a: customer, b: customer) =>
            sort.direction === 'asc'
              ? a.firstName < b.firstName
                ? -1
                : 1
              : b.firstName < a.firstName // descending
              ? -1
              : 1
        )),
      lastname: () =>
        (this.dataSource.data = this.dataSource.data.sort(
          (a: customer, b: customer) =>
            sort.direction === 'asc'
              ? a.lastName < b.lastName
                ? -1
                : 1
              : b.lastName < a.lastName // descending
              ? -1
              : 1
        )),
    };
    literals[sort.active as keyof typeof literals]();
  }
}
