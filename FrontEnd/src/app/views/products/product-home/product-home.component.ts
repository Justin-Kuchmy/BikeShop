import { Component, OnInit } from '@angular/core';
import { Observable, map, switchMap } from 'rxjs';
import { products } from '../models/products';
import { ProductsService } from '@app/views/products/products.service';
import { orders } from '@app/views/orders/models/orders'
import { orderlineitems } from '@app/views/orders/models/orderlineitems';
import { ListObjectWrapper } from '../../../ListObjectWrapper';
import { ProductFilter } from '../models/ProductFilter';

@Component({
  selector: 'app-products',
  templateUrl: './product-home.component.html',
})
export class ProductsHomeComponent implements OnInit {

  Products$!: Observable<ListObjectWrapper<products>>;
  products: products[] = [];
  msg: String = "";
  showDetails: string = "showDetails";
  showProducts: string = "showProducts";
  showCheckOut: string = "showCheckOut";
  selectedComponent: string = "";
  product: products;
  productCart: products[] = [];
  orderList: orderlineitems[] = [];
  order: orderlineitems;
  productFilter: ProductFilter;
  showFilterPopUp: Boolean = false;
  myMap: Map<number, products[]> = new Map<number, products[]>();
  constructor(public productsService: ProductsService) 
  { 
    this.productFilter = {
      productName: "",
      modelYear: 0,
      topPrice: 0,
      bottomPrice: 0
    };

    this.product = {
        productId: 0,
        productName: "",
        brandId: 0,
        categoryId: 0,
        modelYear: 0,
        listPrice: 0,  
      };
      this.order = {
        orderItemId: 0,
        orderId: 0,
        itemId: 0,
        productId: 0,
        quantity: 0,
        listPrice: 0,
        discount: 0.0,
      }
      

  }


  ngOnInit(): void {
    this.selectedComponent = "showProducts";
    this.Products$ = this.productsService.get("PRODUCT", "/all");
    this.Products$.subscribe((prod: ListObjectWrapper<products>) => 
    {
      this.products = prod.objectList;
    })
    console.log(`showing Details: ${this.selectedComponent}`);
  }

  showSearchPopUp()
  {
      this.showFilterPopUp = !this.showFilterPopUp;
  }

  applyFilter(filter: ProductFilter)
  {
    this.productFilter = filter;
    
    this.Products$ = this.Products$.pipe(map((prod: ListObjectWrapper<products>) => 
    {

      var filteredProducts: products[];
      if (prod && !this.filterIsEmpty(filter))
      {
        var filteredProducts = prod.objectList.filter((product: products) => {
          let nameMatch = !filter.productName || product.productName.toLowerCase().includes(filter.productName.toLowerCase());
          let yearMatch = !filter.modelYear || product.modelYear === filter.modelYear;
          let priceMatch = (!filter.topPrice || product.listPrice <= filter.topPrice)
            && (!filter.bottomPrice || product.listPrice >= filter.bottomPrice);
          return nameMatch && yearMatch && priceMatch;  
        });
      }
      else 
      {
        //resets the filter
        filteredProducts = this.products ? this.products : [];
      }

        return new ListObjectWrapper(filteredProducts);
      })
    ); 
  }
  filterIsEmpty(filter: ProductFilter): Boolean 
  {
    var res = filter.productName === "" && filter.modelYear === 0 && filter.bottomPrice === 0 && filter.topPrice === 0;
    return res;
  }
  select(selectedProduct: products): void {
    this.product = selectedProduct;
    this.msg = `product ${selectedProduct.productId} selected`;
    this.selectedComponent = "showDetails";
    
  } // select

  addProductToCart(prod: products): void {
    this.productCart.push(prod);
    this.selectedComponent = "showProducts";
  }

  cancel(msg?: string): void {
    msg ? (this.msg = 'Operation cancelled') : null;
    this.selectedComponent = "showProducts";
  } // cancel

  detailMessage(msg: string): void
  {
    this.msg = msg;
  }

  placeOrder(): void
  {
    this.selectedComponent = "showCheckOut";
    for (const obj of this.productCart) {
      if(!this.myMap.has(obj.productId))
      {
        this.myMap.set(obj.productId, []);
      }
      this.myMap.get(obj.productId)?.push(obj);
      this.myMap.forEach((value, key) => {
      })
       
    }      
  }

}
