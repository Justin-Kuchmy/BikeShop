import { Component, Input } from '@angular/core';
import { orders } from '@app/views/orders/models/orders';

@Component({
  selector: 'app-order-list-object',
  template: `
     <div class="bg-gray-200 hover:bg-gray-500 hover:text-gray-100 cursor-pointer hover:cursor-pointer p-4" *ngIf="order">
      <h2 class="text-lg font-bold">Order Details</h2>
      <div class="grid grid-cols-2 gap-4 text-gray-800 hover:text-gray-100 text-lg font-bold py-0">
        <p><strong>Order ID:</strong></p><p>{{ order.orderId }}</p>
        <p><strong>Customer ID:</strong></p><p>{{ order.customerId }}</p>
        <p><strong>Order Status:</strong></p><p>{{ order.orderStatus }}</p>
        <p><strong>Order Date:</strong></p><p>{{ formatDate(order.orderDate) | date }}</p>
        <p><strong>Required Date:</strong></p><p>{{formatDate(order.requiredDate) | date }}</p>
        <p><strong>Shipped Date:</strong></p><p>{{ formatDate(order.shippedDate) | date }}</p>
        <p><strong>Store ID:</strong></p><p>{{ order.storeId }}</p>
        <p><strong>Staff ID:</strong></p><p>{{ order.staffId }}</p>
      </div>
    </div>
  <br>
    
  `
})
export class OrderListObjectComponent {
    @Input() order!: orders;

    formatDate(dateString: string) {
        if (dateString === null || dateString === "") {
            return null;
        }

        else 
        { 
            var date = `${dateString.substring(0, 4)}-${dateString.substring(4, 6)}-${dateString.substring(6, 8)}T00:00:00`;
            return date;
        }
            
      }

}
