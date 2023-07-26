package com.justinkuchmy.order.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Orders")
public class Order implements Serializable{
    
    private Long orderId;
    private Long customerId;
    private String orderStatus = null;
    private String orderDate = null;
    private String requiredDate = null;
    private String shippedDate = null;
    private Long storeId;
    private Long staffId;  

    public Order
    (
        Long customerId,
        String orderStatus,
        String orderDate,
        String requiredDate,
        String shippedDate,
        Long storeId,
        Long staffId    
    )
    {
        this.customerId    =customerId;
        this.orderStatus   =orderStatus;
        this.orderDate     =orderDate;
        this.requiredDate  =requiredDate;
        this.shippedDate   =shippedDate;
        this.storeId       =storeId;
        this.staffId       =staffId;
    }

    @Transient
    private List<Orderitem> orderItems = new ArrayList<Orderitem>();

    public void AddOrderItem(Orderitem order){this.orderItems.add(order);}
}


