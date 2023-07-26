import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeleteDialogComponent } from '../delete-dialog.component';



@NgModule({
  declarations: [DeleteDialogComponent],
  imports: [
    CommonModule
  ],
  exports: [
    DeleteDialogComponent
  ]
})
export class DeleteModule { }
