import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatComponentsModule } from '@app/views/mat-components/mat-components.module';
import { ProductsHomeComponent } from './product-home/product-home.component';
import { ProductListComponent } from './product-list/product-list.component';
import { ProductDetailsComponent } from './product-details/product-details.component';
import { ProductCheckoutComponent } from './product-checkout/product-checkout.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ConfirmationModalComponent } from './product-checkout/confirmation-modal/confiramtion-modal.component';
import { ProductFilterComponent } from './product-filter/product-filter.component';



@NgModule({
  declarations: [
    ProductsHomeComponent,
    ProductListComponent,
    ProductDetailsComponent,
    ProductCheckoutComponent,
    ConfirmationModalComponent,
    ProductFilterComponent
  ],
  imports: [
    CommonModule,
    MatComponentsModule,
    ReactiveFormsModule,
    FormsModule,
  ],
  exports: [

  ]
})
export class ProductsModule { }
