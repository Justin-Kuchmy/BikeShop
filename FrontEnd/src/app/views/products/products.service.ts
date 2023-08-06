import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { GenericHttpService } from '@app/generic-http.service';
import { products } from './models/products';
import { Constants } from '@app/constants';
import { Observable, retry, catchError } from 'rxjs';
import { UserDTO } from '../auth/UserDTO';
@Injectable({
  providedIn: 'root',
})
export class ProductsService extends GenericHttpService<products> {
  private productNames: Map<number, string> = new Map<number, string>();
  constructor(httpClient: HttpClient) {
    
    var token = sessionStorage.getItem('token');
    super(httpClient, `products`);
  } // constructor

    //helper method to save project name locally 
    setProductName(productId: number, productName: string): void {
      this.productNames.set(productId, productName);
    }
  
    //helper method to get project name for client side
    getProductName(productId: number): string | undefined {
      return this.productNames.get(productId);
    }

  /**
  if user has a existing orders or account, it will prefill some of checkout details
  @param string which contains that user email
  @return a user object which all the matching user details
  */
  public loadUserDetailsForCheckOut(details: string): Observable<UserDTO> {
    var res = this.getHttpClient()
    .get<UserDTO>(`${Constants.getBaseUrl()}User/${details}`)
    .pipe(retry(2), catchError(this.handleError));
    return res;
  }

  /**
  Takes the orders productid and returns the matching product name for the order summary.
  @param number representation of the product id
  @return a string of the matching product name.
  */
  public getProductNameByID(ProductID: number): Observable<string>
  {
    var res = this.getHttpClient().get(`http://localhost:8084/api/v1/product/name/` + ProductID, {responseType: 'text'});
    return res;
  }
}


