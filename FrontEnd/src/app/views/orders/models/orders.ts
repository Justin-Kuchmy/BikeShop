import { orderlineitems } from "./orderlineitems";

export interface orders 
{
    orderId: number;
    customerId: number;
    orderStatus: string;
    orderDate: string;
    requiredDate: string;
    shippedDate: string;
    storeId: number;
    staffId: number;
    orderItems: orderlineitems[];
}