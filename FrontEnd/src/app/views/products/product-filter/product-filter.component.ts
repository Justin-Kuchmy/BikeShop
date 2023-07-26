import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ProductFilter } from '../models/ProductFilter';

@Component({
  selector: 'app-product-filter',
  template: `
<div class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
  <div class="bg-white p-4 rounded-lg shadow-md">
    <label class="block mb-2 font-bold" for="product-name">Product Name:</label>
    <input class="w-full mb-4 px-2 py-1 border rounded" type="text" id="product-name" [(ngModel)]="productName" />

    <label class="block mb-2 font-bold" for="model-year">Model Year:</label>
    <input class="w-full mb-4 px-2 py-1 border rounded" type="number" id="model-year" [(ngModel)]="modelYear" />

    <label class="block mb-2 font-bold" for="list-price-min">List Price Min</label>
    <input class="w-1/2 ml-2 mb-4 px-2 py-1 border rounded" type="number" id="list-price-max" [(ngModel)]="bottomPrice" />
    <label class="block mb-2 font-bold" for="list-price-min">List Price Max:</label>
    <input class="w-1/2 mr-2 mb-4 px-2 py-1 border rounded" type="number" id="list-price-min" [(ngModel)]="topPrice" />

    <div class="text-right">
      <button class="px-4 py-2 text-white bg-blue-500 rounded" (click)="applyFilter()">Apply Filter</button>
      <button class="px-4 py-2 text-white bg-red-500 rounded ml-2" (click)="cancelFilter()">Cancel</button>
    </div>
  </div>
</div>

  `,
  styles: [
  ]
})
export class ProductFilterComponent {
@Output() cancelled = new EventEmitter<Boolean>()
@Output() filterApplied = new EventEmitter<ProductFilter>();

FilterToApply: ProductFilter = {
  productName: "",
  modelYear: 0,
  topPrice: 0,
  bottomPrice: 0,
};
  productName: string = "";
  modelYear: number = 0;
  topPrice: number = 0;
  bottomPrice: number = 0;
cancelFilter() {
  this.cancelled.emit(false);
}
applyFilter() {
  this.FilterToApply.productName = this.productName;
  this.FilterToApply.modelYear = this.modelYear;
  this.FilterToApply.topPrice = this.topPrice;
  this.FilterToApply.bottomPrice = this.bottomPrice;
  this.filterApplied.emit(this.FilterToApply);
  this.cancelled.emit(false);
}

}
