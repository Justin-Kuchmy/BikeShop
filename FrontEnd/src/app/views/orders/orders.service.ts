import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { GenericHttpService } from '@app/generic-http.service';
import { orders } from './models/orders';
import { Constants } from '@app/constants';
import { Observable, retry, catchError } from 'rxjs';
import { orderlineitems } from './models/orderlineitems';
import { ListObjectWrapper } from '@app/ListObjectWrapper';

@Injectable({
  providedIn: 'root',
})
export class OrdersService extends GenericHttpService<orders> {
  constructor(httpClient: HttpClient) {
    var token = sessionStorage.getItem('token');
    super(httpClient, `orders`);
  } // constructor

  public getAdminAuthorization(): Observable<string[]> {

    var header = {
        headers: new HttpHeaders()
          .set('Authorization',  `Bearer ${sessionStorage.getItem('token')}`)
      }

    var res = this.getHttpClient().get<string[]>(`${Constants.getBaseUrl()}authorize`, header)
    .pipe(retry(0), catchError(this.handleError));
    return res;
  }
  
  public getOrdersAuthReq(): Observable<orders[]> {
    var header = {
        headers: new HttpHeaders()
          .set('Authorization',  `Bearer ${sessionStorage.getItem('token')}`)
      }
    return this.getHttpClient()
      .get<orders[]>(`${Constants.getBaseUrl()+Constants.ORDER_URL}orders`, header)
      .pipe(retry(2), catchError(this.handleError));
  } // getAll

  public getCustomersOrders(customerID: any): Observable<orders[]> {
    var header = {
        headers: new HttpHeaders()
          .set('Authorization',  `Bearer ${sessionStorage.getItem('token')}`)
      }
    var response = this.getHttpClient()
    .get<orders[]>(`${Constants.getBaseUrl()+Constants.ORDER_URL}orders/customer/items/${customerID}`, header)
    .pipe(retry(2), catchError(this.handleError));
    return response;
  } // get all of a customers orders

  public getOrdersItems(orderID: any): Observable<ListObjectWrapper<orderlineitems>> {
    var header = {
        headers: new HttpHeaders()
          .set('Authorization',  `Bearer ${sessionStorage.getItem('token')}`)
      }
    var response = this.getHttpClient()
    .get<ListObjectWrapper<orderlineitems>>(`${Constants.getBaseUrl()+Constants.ORDERITEM_URL}orderitems/OrderID/${orderID}`, header)
    .pipe(retry(2), catchError(this.handleError));
    return response;
  } // get all items of a specific order

  public addOrdersItems(orderitems: orderlineitems): Observable<any> 
  {
    var data = this.getHttpClient()
    .post<orderlineitems>(`${Constants.getBaseUrl()+Constants.ORDERITEM_URL}orderitems`, orderitems)
    .pipe(retry(0), catchError(this.handleError));
  return data;
  }

  public deleteOrderByID(OrderId: number): Observable<string>
  {
    var res = this.getHttpClient()
    .delete(`${Constants.ORDER_URL}order/delete/` + OrderId, {responseType: 'text'})
    .pipe(retry(0), catchError(this.handleError));
    return res;
  }
}
