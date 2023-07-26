import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { GenericHttpService } from '@app/generic-http.service';
import { customers } from './models/customers';
import { Constants } from '@app/constants';
import { Observable, retry, catchError } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class CustomersService extends GenericHttpService<customers> {
    
  constructor(httpClient: HttpClient) {
        super(httpClient, `customers`,);

  }

  public getAdminAuthorization(): Observable<string[]> {

    var header = {
        headers: new HttpHeaders()
          .set('Authorization',  `Bearer ${sessionStorage.getItem('token')}`)
      }

    var res = this.getHttpClient().get<string[]>(`${Constants.getBaseUrl()}authorize`, header)
    .pipe(retry(0), catchError(this.handleError));
    return res;
  }

  public getCustomersAuthReq(): Observable<customers[]> {
    var header = {
        headers: new HttpHeaders()
          .set('Authorization',  `Bearer ${sessionStorage.getItem('token')}`)
      }
    return this.getHttpClient()
      .get<customers[]>(`${Constants.getBaseUrl()}customers`, header)
      .pipe(retry(2), catchError(this.handleError));
  } // getAll

  public getCustomerByEmail(email: string): Observable<any>
  {
    var res = this.getHttpClient()
    .get<any>(`${Constants.getBaseUrl()}customers/email/${email}`)
    .pipe(retry(0), catchError(this.handleError));
    return res;
  }


  public getByIDAuthReq(id: number): Observable<customers> {
    var header = {
        headers: new HttpHeaders()
          .set('Authorization',  `Bearer ${sessionStorage.getItem('token')}`)
      }
    return this.getHttpClient()
      .get<customers>(`${Constants.getBaseUrl()}customers/id/${id}`, header)
      .pipe(retry(2), catchError(this.handleError));
  } // getSome

  public getByStringAuthReq(prop: String, value: String): Observable<customers[]> {
    var header = {
        headers: new HttpHeaders()
          .set('Authorization',  `Bearer ${sessionStorage.getItem('token')}`)
      }


    return this.getHttpClient()
      .get<customers[]>(`${Constants.CUSTOMERSEARCH}prop=${prop}&value=${value}`, header)
      .pipe(retry(2), catchError(this.handleError));
  } // getSome

}
