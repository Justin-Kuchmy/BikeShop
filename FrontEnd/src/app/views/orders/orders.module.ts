import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatComponentsModule } from '@app/views/mat-components/mat-components.module';
import { OrderHomeComponent } from './order-home/order-home.component';
import { OrderDetailsComponent } from './order-details/order-details.component';
import { SearchModule } from '../search/search.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CustomModalModule } from '@app/custom-modal/custom-modal.module';
import { DeleteModule } from '../delete-dialog/delete/delete.module';


@NgModule({
  declarations: [
    OrderHomeComponent,
    OrderDetailsComponent,
  ],
  imports: [
    CommonModule,
    MatComponentsModule,
    ReactiveFormsModule,
    SearchModule,
    FormsModule,
    CustomModalModule,
    DeleteModule
  ],
  exports: [
    OrderHomeComponent,
    OrderDetailsComponent,
  ]
})
export class OrdersModule { }
