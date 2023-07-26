import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableSearchComponent } from './table-search/table-search.component';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: 
  [
    TableSearchComponent,
  ],
  imports: [
    CommonModule,
    FormsModule, 
  ],
  exports: [
    TableSearchComponent
  ]
})
export class SearchModule { }
