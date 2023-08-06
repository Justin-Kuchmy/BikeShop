import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { GenericHttpService } from '@app/generic-http.service';
import { orders } from './models/orders';

@Injectable({
  providedIn: 'root',
})
export class OrdersService extends GenericHttpService<orders> {
  constructor(httpClient: HttpClient) {
    super(httpClient, `orders`);
  } // constructor
}
