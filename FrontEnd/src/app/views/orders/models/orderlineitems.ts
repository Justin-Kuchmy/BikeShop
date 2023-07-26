export interface orderlineitems
{
    orderItemId: number | null;
    orderId: number;
    itemId: number;
    productId: number;
    quantity: number;
    listPrice: number;
    discount: number;
}