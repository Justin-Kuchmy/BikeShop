import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { GenericHttpService } from '@app/generic-http.service';
import { customer } from './models/customer';
import { Constants } from '@app/constants';
import { Observable, retry, catchError } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class CustomersService extends GenericHttpService<customer> {
    
  constructor(httpClient: HttpClient) {
        super(httpClient, `customers`,);

  }

  //used for order checkout to get existing customer details via email.
  public getCustomerByEmail(email: string): Observable<any>
  {
    var res = this.getHttpClient()
    .get<any>(`${Constants.getBaseUrl()}customers/email/${email}`)
    .pipe(retry(0), catchError(this.handleError));
    return res;
  }

}
