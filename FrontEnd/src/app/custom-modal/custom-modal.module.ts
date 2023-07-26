import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { CustomModalComponent } from './custom-modal.component';



@NgModule({
  declarations: [
    CustomModalComponent,
    
],
  imports: [
    CommonModule,
    MatButtonModule,
    MatDialogModule,  
    
  ],
  exports: [
    CustomModalComponent,
  ]
})
export class CustomModalModule { }
