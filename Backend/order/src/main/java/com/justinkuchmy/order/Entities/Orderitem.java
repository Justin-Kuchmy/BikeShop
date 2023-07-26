package com.justinkuchmy.order.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orderitem {
    public Orderitem
    (
        Long order_id,
        Long item_id,
        Long product_id,
        Long quantity,
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
    private Long     orderItemId;
    private Long       orderId;
    private Long       itemId;
    private Long       productId;
    private Long       quantity;
    private double   listPrice;
    private double   discount;
}
