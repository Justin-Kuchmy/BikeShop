import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatComponentsModule } from '@app/views/mat-components/mat-components.module';
import { CustomerListComponent } from './customer-list/customer-list.component';
import { CustomerHomeComponent } from './customer-home/customer-home.component';
import { ReactiveFormsModule } from '@angular/forms';
import { SearchFilterPipe } from './customer-home/SearchFilterPipe'
import { FormsModule } from '@angular/forms';
import { CustomerDetailsComponent } from './customer-details/customer-details.component';
import { CustomersOrdersComponent } from './customer-orders/customer-orders.component';
import { CustomersOrdersListComponent } from './customer-orders/customers-orders-list/customers-orders-list.component';
import { OrderListObjectComponent } from './customer-orders/customers-orders-list/order-list-object/order-list-object.component';
import { CustomModalComponent } from '@app/custom-modal/custom-modal.component';
import { CustomModalModule } from '@app/custom-modal/custom-modal.module';


@NgModule({
  declarations: [
    CustomerListComponent,
    CustomerHomeComponent,
    SearchFilterPipe,
    CustomerDetailsComponent,
    CustomersOrdersListComponent,
    OrderListObjectComponent,
    CustomersOrdersComponent
  ],
  imports: [
    CommonModule,
    MatComponentsModule,
    ReactiveFormsModule,
    FormsModule,
    CustomModalModule
  ],
  exports: []
})
export class CustomersModule { }
