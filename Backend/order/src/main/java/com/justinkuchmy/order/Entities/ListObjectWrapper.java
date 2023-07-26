package com.justinkuchmy.order.Entities;

import java.util.List;
import lombok.Data;

@Data
public class ListObjectWrapper<T> {
    public ListObjectWrapper(){}
    public ListObjectWrapper(List<T> objectList)
    {
        this.objectList = objectList;
    }

    private List<T> objectList;

}