import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { products } from '../models/products';
import { ListObjectWrapper } from '../../../ListObjectWrapper';
import { ProductFilter } from '../models/ProductFilter';

@Component({
  selector: 'app-product-list',
  template: `
    <mat-grid-list cols="3" class="product-list">
      <mat-grid-tile class="product-tile" *ngFor="let product of products?.objectList" (click)="listSelect(product)">
        <div class="max-w-md mx-auto rounded shadow-lg overflow-hidden">
          <a
            class="block hover:bg-gray-100 transition duration-300 ease-in"
          >
            <img
              class="w-full h-48 object-cover"
              src="./assets/bike.png"
              alt="Project Image"
            />
            <div class="p-4">
              <h3 class="text-xl font-bold mb-2">{{ product.productName }}</h3>
              <h4 class="font-bold mb-2">
                Price: {{ '$' }}{{ product.listPrice }}
              </h4>
            </div>
          </a>
        </div>
      </mat-grid-tile>
    </mat-grid-list>
  `,
  styles: [
    `
      .product-list {
        border: 0px solid blue;
      }
      .product-tile {
        border: 0px solid black;
      }

      .product-card {
        height: 250px;
        width: 250px;
        text-align: center;
        box-shadow: 5px;
      }

      .product-img {
        position: absolute;
        height: 275px;
        width: 275px;
        left: 0;
        top: 0;
      }
    `,
  ],
})
export class ProductListComponent {
  @Input() products?: ListObjectWrapper<products>;
  @Output() selectedItem = new EventEmitter<products>();

  listSelect(selectedProduct: products) {
    this.selectedItem.emit(selectedProduct);
  }
  
}
