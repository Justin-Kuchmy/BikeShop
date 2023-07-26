package com.justinkuchmy.customer.Entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    public OrderItem
    (
        int order_id,
        int item_id,
        int product_id,
        int quantity,
        double list_price,
        double discount
    )
    {
        this.orderId = order_id;
        this.itemId = item_id;
        this.productId = product_id;
        this.quantity = quantity;
        this.listPrice = list_price;
        this.discount = discount;   
    }
    private Long    orderItemId;
    private int      orderId;
    private int      itemId;
    private int      productId;
    private int      quantity;
    private double  listPrice;
    private double  discount;
}
