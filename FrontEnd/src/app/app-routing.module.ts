import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './views/home/home.component';
import { CustomerHomeComponent } from './views/customers/customer-home/customer-home.component';
import { OrderHomeComponent } from './views/orders/order-home/order-home.component';
import { ProductsHomeComponent } from './views/products/product-home/product-home.component';
import { IndexComponent } from './layouts/index/index.component';
import { AuthComponent } from './layouts/auth/auth.component';
import { LoginComponent } from './views/auth/login/login.component';
import { RegisterComponent } from './views/auth/register/register.component';


const routes: Routes = [
    //no layouts
    {path: '', redirectTo: "api", pathMatch: "full" },
    {
        path: 'api',
        children: [
            { path: '', redirectTo: "index", pathMatch: "full" },
            {
                path: "index",
                component: IndexComponent,
                children: [
                    { path: '', redirectTo: "home", pathMatch: "full" },
                    { path: 'home', component: HomeComponent, title: 'Home' },
                    { path: 'customers', component: CustomerHomeComponent, title: 'Customers' },
                    { path: 'orders', component: OrderHomeComponent, title: 'Orders' },
                    { path: 'products', component: ProductsHomeComponent, title: 'Products'}
                ]
            },
            // auth views
            {
                path: "auth",
                component: AuthComponent,
                children: [
                    { path: 'login', component: LoginComponent, title: 'J&J Login'},
                    { path: 'register', component: RegisterComponent, title: 'J&J Register'},
                    { path: "", redirectTo: "login", pathMatch: "full"}
                ]
            }
        
        ]
    },
   
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
