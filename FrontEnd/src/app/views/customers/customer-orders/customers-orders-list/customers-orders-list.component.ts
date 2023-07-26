import { Component, OnInit,Output, EventEmitter, Input } from '@angular/core';
import { ListObjectWrapper } from '@app/ListObjectWrapper';
import { orders } from '@app/views/orders/models/orders';

@Component({
  selector: 'app-customers-orders-list',
  template: `

  
  <div 
  *ngFor="let order of orders.objectList let i = index"
  class="mb-4"
  (click)="selectedItem.emit(order)" > 
    <app-order-list-object 
        [order]="order"
        >
    </app-order-list-object>
  </div >
`,
})
export class CustomersOrdersListComponent {
    @Input() orders!: ListObjectWrapper<orders>;
    @Output() selectedItem = new EventEmitter<orders>();
    listSelect(selectedOrder: orders)
    {
      this.selectedItem.emit(selectedOrder);
    }
  

    dateFormat(date: String): String
    {
        return [date.slice(0, 4), "-", date.slice(4, 6), "-", date.slice(6)].join('');;
    }

}
