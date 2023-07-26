import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { GenericHttpService } from '@app/generic-http.service';
import { products } from './models/products';
import { Constants } from '@app/constants';
import { Observable, retry, catchError, switchMap } from 'rxjs';
import { Router } from '@angular/router'
import { customers } from '../customers/models/customers';
import { UserDTO } from '../auth/UserDTO';
import { ListObjectWrapper } from '@app/ListObjectWrapper';
@Injectable({
  providedIn: 'root',
})
export class ProductsService extends GenericHttpService<products> {
  private productNames: Map<number, string> = new Map<number, string>();
  constructor(httpClient: HttpClient) {
    
    var token = sessionStorage.getItem('token');
    super(httpClient, `products`);
  } // constructor

    setProductName(productId: number, productName: string): void {
      this.productNames.set(productId, productName);
    }
  
    getProductName(productId: number): string | undefined {
      return this.productNames.get(productId);
    }

  public getProductsNoAuth(): Observable<ListObjectWrapper<products>> {
    var getInstancePort = Constants.getDiscoveryServerUrl() + "PRODUCT";
    
    //Get the port number for the product microservice.
    // could also return an object with the host and port. ie a public 
    // ip address so we dont have to hardcode localhost
    var port$: Observable<number> = this.getHttpClient().get<number>(getInstancePort)
    .pipe(retry(0), catchError(this.handleError));

    var response$: Observable<ListObjectWrapper<products>> = port$.pipe(
        switchMap((port: number) =>
          this.getHttpClient()
            .get<ListObjectWrapper<products>>(`http://localhost:${port}/api/v1/products/all`)
            .pipe(retry(0), catchError(this.handleError))
        )
      );
    return response$;
  } // get all of a customers orders

  public loadUserDetailsForCheckOut(dets: string): Observable<UserDTO> {
    var res = this.getHttpClient()
    .get<UserDTO>(`${Constants.getBaseUrl()}User/${dets}`)
    .pipe(retry(2), catchError(this.handleError));
    return res;
  }

  public getProductNameByID(ProductID: number): Observable<string>
  {
    var res = this.getHttpClient().get(`http://localhost:8084/api/v1/product/name/` + ProductID, {responseType: 'text'});
    return res;
  }
}


