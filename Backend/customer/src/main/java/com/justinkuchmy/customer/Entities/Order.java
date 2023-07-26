package com.justinkuchmy.customer.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Orders")
public class Order implements Serializable {
    public Order
    (
        Long     customer_id,
        String  order_status,
        String  order_date,
        String  required_date,
        String  shipped_date,
        Long     store_id,
        Long     staff_id
    )
    {
        this.customerId = customer_id;
        this.orderStatus = order_status;
        this.orderDate = order_date;
        this.requiredDate = required_date;
        this.shippedDate = shipped_date;
        this.storeId = store_id;
        this.staffId  = staff_id;
    }
    private Long orderId;
    private Long customerId;
    private String orderStatus = null;
    private String orderDate = null;
    private String requiredDate = null;
    private String shippedDate = null;
    private Long storeId;
    private Long staffId;  

    @Transient
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();
}
