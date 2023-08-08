import { Inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { retry, catchError, map, switchMap } from 'rxjs/operators';
import { Constants } from '@app/constants';
import { CustomModalComponent } from '@app/custom-modal/custom-modal.component';
import { orders } from './views/orders/models/orders';
import { ListObjectWrapper } from './ListObjectWrapper';
@Injectable({
  providedIn: 'root',
})
export class GenericHttpService<T> {
  constructor(private httpClient: HttpClient, @Inject(String) private entity: string, 
    ) 
  {
    
  } // constructor
 public getHttpClient(): HttpClient 
 {
return this.httpClient;
 }

 public checkAuthorization(): Observable<any>
 {

   var header = {headers: new HttpHeaders()
       .set('Authorization',  `Bearer ${sessionStorage.getItem('token')}`)
     }
      var discoveryServerUrl = Constants.getDiscoveryServerUrl()+"security";
      var port$: Observable<number> = this.httpClient.get<number>(discoveryServerUrl).pipe(); 
      var result = port$.pipe(
        switchMap((port: number) =>
            {
                var url = Constants.getUrlForEntity(port, "authorize")!;
               
                return this.httpClient.get(url, header);
            }
        )
    );
     return result;

   
 }
 private getServiceUrl(serviceName: string): Observable<number> {
    var discoveryServerUrl = Constants.getDiscoveryServerUrl() +serviceName;
    var port$: Observable<number> = this.getHttpClient().get<number>(discoveryServerUrl)
        .pipe(retry(0), catchError(this.handleError));
    return port$;
  }

  private sendRequest<T>(method: string, serviceName: string, path: string, data?: any): Observable<any> {
    var headers = new HttpHeaders().set('Authorization',  `Bearer ${sessionStorage.getItem('token')}`);
    return this.getServiceUrl(serviceName).pipe(
        switchMap((port: number) =>
          {
            var url = Constants.getUrlForEntity(port, serviceName.toLowerCase()) + path;
            switch (method) {  
            case 'GET':
              return  this.httpClient.get<ListObjectWrapper<T>>(url, { headers });
            case 'POST':
              return this.httpClient.post<ListObjectWrapper<T>>(url, data, { headers });
            case 'PUT':
              return this.httpClient.put<ListObjectWrapper<T>>(url, data, { headers });
            case 'DELETE':
              return this.httpClient.delete(url, { headers });
            default:
              throw new Error(`Invalid HTTP method: ${method}`);
          }}
        ), catchError(error => {return throwError(error);})
      );
     //return serviceUrl$;
  }
 public get<T>(serviceName: string, path: string): Observable<T> {
    var res = this.sendRequest<T>('GET', serviceName, path);
    return res;
  }

  public post<T>(serviceName: string, path: string, data: any): Observable<T> {
    return this.sendRequest<T>('POST', serviceName, path, data);
  }

  public put<T>(serviceName: string, path: string, data: any): Observable<T> {
    return this.sendRequest<T>('PUT', serviceName, path, data);
  }

  public delete<T>(serviceName: string, path: string): Observable<T> {
    var res = this.sendRequest<T>('DELETE', serviceName, path);
    return res;
  }


  public getByID(serviceName: string, path: string): Observable<T> {
    var header = {headers: new HttpHeaders()
        .set('Authorization',  `Bearer ${sessionStorage.getItem('token')}`)
      }
    const serviceUrl$ = this.getServiceUrl(serviceName);
    var response$: Observable<T> = serviceUrl$.pipe(
        switchMap((port: number) =>
          {
            var url = Constants.getUrlForEntity(port, serviceName) + path;
            return this.httpClient.get<T>(url, header);
            }
        )
      );
    return response$;
  } // getSome

  public getByString(prop: String, value: String): Observable<T[]> {
    var header = {headers: new HttpHeaders()
        .set('Authorization',  `Bearer ${sessionStorage.getItem('token')}`)
      }
    return this.httpClient
      .get<T[]>(`${Constants.CUSTOMERSEARCH}prop=${prop}&value=${value}`, header)
      .pipe(retry(0), catchError(this.handleError));
  } // getSome

  // Error handling
  handleError(error: any) {
    let status: any;
    error.error instanceof ErrorEvent
      ? // Get client-side error
        (status = error.error.message)
      : // Get server-side error
        (status = `Error Code: ${error.status}\nMessage: ${error.message}`);

    return throwError(() => error.status);
  }
} // GenericHttpService
