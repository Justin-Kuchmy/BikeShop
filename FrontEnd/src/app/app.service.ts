import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { catchError, map, Observable, switchMap } from 'rxjs';
import { Constants } from './constants';

@Injectable()
export class AppService {
  authenticated = false;

  constructor(private http: HttpClient) {}

  register(credentials: any): any 
  {
    const creds = 
    {
        "firstName": `${credentials.firstname}`,
        "lastName":`${credentials.lastname}`,
        "email":`${credentials.email}`,
        "password":`${credentials.password}`
    };
    var result = this.http
      .post(`${Constants.getBaseUrl()}auth/signUp`, creds)
      .subscribe((response:  Map<String, String> | any) => {
        return response['token'];
      });
      return result;
  }
  authenticate(credentials: any): any { 
    var discoveryServerUrl = Constants.getDiscoveryServerUrl()+"security";
    var port$: Observable<number> = this.http.get<number>(discoveryServerUrl).pipe(); 
    var result = port$.pipe(
        switchMap((port: number) =>
            {
                var url = Constants.getUrlForEntity(port, "auth/signIn")!;
                var body = {"login":credentials.email,"password":credentials.password};
                return this.http.post(url, body, {responseType: 'text'});
            }
        )
    );
    //var url = `${Constants.getBaseUrl()}auth/signIn`;
    //var body = {"login":credentials.email,"password":credentials.password};

    //var result = this.http.post(url, body, {responseType: 'text'});
    return result;
  }
}

