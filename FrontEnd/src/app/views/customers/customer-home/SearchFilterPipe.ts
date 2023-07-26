import {Injectable, Pipe, PipeTransform} from "@angular/core"
import { Observable } from "rxjs";
import { CustomersService } from "../customers.service";


@Pipe({
    name:"searchFilter",
    pure: false
})
@Injectable({
    providedIn: 'root',
  })
export class SearchFilterPipe implements PipeTransform {
    constructor(public customersService: CustomersService){}
    transform(value: number) : Observable<any> {
        var result = this.customersService.getByID("customer", "id/"+value); 
        return result;
    }
    getByString(prop: String, value: String) : Observable<any> {
        var result = this.customersService.getByString(prop, value);
        return result;
    }

}