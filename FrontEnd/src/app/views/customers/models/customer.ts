import { orders } from "@app/views/orders/models/orders";

export interface customer 
{
    customerId: number;
    customerOrders: orders[];
    firstName: String;
    lastName: String;
    phone: String;
    email: String;
    street: String;
    city: String;
    state: String;
    zipCode: String;
}