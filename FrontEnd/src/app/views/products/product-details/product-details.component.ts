import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { products } from '../models/products';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styles: [`.product-details-card
            {
                height: 200x;
            }

            .product-details-list
            {
                width: 50%;
            }
            .product-details-item {
                border: 1px solid black;
                box-shadow: 5px;
            }
        `]
})
export class ProductDetailsComponent implements OnInit{
    @Input() selectedProduct: products = {
        productId: 0,
        productName: "",
        brandId: 0,
        categoryId: 0,
        modelYear: 0,
        listPrice: 0,  
      };
      msg: string = "";
      hideProductPage: boolean = true;
      @Input() products: products[] | null = null;
      @Output() cancelled = new EventEmitter();
      @Output() productToAdd = new EventEmitter();
      @Output() message = new EventEmitter();

      ngOnInit(): void {
    }

    buttonClicked(button: string): void
    {
        if(button == 'cancel')
            this.cancelled.emit('cancelled')
        else if (button == 'add'){
            this.productToAdd.emit(this.selectedProduct)
            this.message.emit(`product ${this.selectedProduct.productId} added`)
        }
    }
 
}
