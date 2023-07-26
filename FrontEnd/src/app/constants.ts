export class Constants
{
    private static IP = 'localhost';
    private static BASEURL = `http://${this.IP}:`;
    public static CUSTOMER_URL = this.BASEURL +`8081/api/v1/`;
    public static ORDER_URL = this.BASEURL +`8082/api/v1/`;
    public static ORDERITEM_URL = this.BASEURL +`8083/api/v1/`;
    public static PRODUCT_URL = this.BASEURL +`8084/api/v1/`;
    public static CUSTOMERSEARCH = this.CUSTOMER_URL + `customers/search?`;
    public static DISCOVERY_SERVER = this.BASEURL + `8761/get-service/`;
    private static myMap: Map<string, number> = new Map<string, number>([
        ['customers', 8081],
        ['orders', 8082],
        ['orderitems', 8083],
        ['products', 8084],
    ]);

    public static getBaseUrl (): any
    {
        return this.BASEURL;
    }
    public static getDiscoveryServerUrl() : any
    {
        return this.DISCOVERY_SERVER;
    }
    public static getUrlForEntity(port: number, key: string): string | undefined {
        //key = key.toLowerCase();
        return this.BASEURL + port + `/api/v1/` + key;
      }

}
