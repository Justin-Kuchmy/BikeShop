package com.portfolioprojects.BikeShop.Sales.Entities.OrderItem;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table
public class Order_items {
    public Order_items(){}
    public Order_items
    (
        int order_id,
        int item_id,
        int product_id,
        int quantity,
        double list_price,
        double discount
    )
    {
        this.order_id = order_id;
        this.item_id = item_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.list_price = list_price;
        this.discount = discount;   
    }
    @Id
    @SequenceGenerator(
        name = "orderitem_sequence",
        sequenceName = "orderitem_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "orderitem_sequence"
    )
    private int order_item_id;
    private int     order_id;
    private int     item_id;
    private int     product_id;
    private int     quantity;
    private double  list_price;
    private double  discount;
}
