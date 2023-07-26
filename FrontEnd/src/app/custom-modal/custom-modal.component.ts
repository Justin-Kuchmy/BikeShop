import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Inject, Input, Output } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-custom-modal',
//   standalone: true,
//   imports: [CommonModule, ReactiveFormsModule, FormsModule, RouterModule],
  templateUrl: './custom-modal.component.html',
})
export class CustomModalComponent {
    @Input() Title: string| null = null;
    @Input() Message: string| null = null;
    @Output() close = new EventEmitter<void>();
  closeDialog(): void {
    this.close.emit();
  }

  constructor(
    //public dialogRef: MatDialogRef<CustomModalComponent>,
    //@Inject(MAT_DIALOG_DATA) public data: any
  ) {}

//   get dialogTitle(): string {
//     return this.data.title;
//   }

//   get dialogContent(): string {
//     return this.data.content;
//   }
}
