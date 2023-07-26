import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { SearchData } from '@app/views/search/SearchData';
import { Observable } from 'rxjs';


@Component({
    selector: 'app-table-search',
    templateUrl: './table-search.component.html'
    
  })
export class TableSearchComponent implements OnInit{
    @Output() public stringEvent = new EventEmitter<SearchData>();
    @Input() ColumnOptions: string[] = [];
    searchName: string = "";
    isHidden = true;
    DropdownButtonText: string = "Column";
    selectedOption: string = "";
    ngOnInit(): void {}
    ChangeDropdownText(option: string)
    {
        this.selectedOption = option;
        this.DropdownButtonText = this.selectedOption;
        this.HideOptions();
    }
    HideOptions():void
    {
        this.isHidden = !this.isHidden
    }
    search(value: any) 
    {
        var data: SearchData = {Column: this.selectedOption, Value: value};
        this.stringEvent.emit(data);
    }
}
