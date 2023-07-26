package com.justinkuchmy.customer.CustomerSearchFactory;
import com.justinkuchmy.customer.Entities.Customer;
import com.justinkuchmy.customer.Entities.ListObjectWrapper;

public interface ICustomerSearchFactory {
    ListObjectWrapper<Customer> search(String value);
}
