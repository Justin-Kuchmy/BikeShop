import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-confirmation-modal',
  template: `
    <div class="fixed inset-0 flex items-center justify-center bg-gray-900 bg-opacity-50">
      <div class="bg-white rounded p-6 w-1/2">
        <h2 class="text-lg font-semibold mb-4">Order Submitted</h2>
        <p class="mb-4">
          Customer ID: {{ customerId }}<br>
          Order ID: {{ orderId }} <br>
          Customer name: {{ customerName }}<br>
          Total: $ {{totalCostAfterTax}}
        </p>
        <div class="flex justify-end">
          <button class="px-4 py-2 bg-blue-500 text-white rounded"(click)="confirmClicked()">Continue</button>
        </div>
      </div>
    </div>
  `,
})
export class ConfirmationModalComponent {
  @Input() customerId: number | null = null;
  @Input() orderId: number| null = null;;
  @Input() customerName: string| null = null;;
  @Input() totalCostAfterTax: number| null = null;;
  @Output() confirm = new EventEmitter<boolean>();

  confirmClicked()
  {
    this.confirm.emit(false);
  }
}
