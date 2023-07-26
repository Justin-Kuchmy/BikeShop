package com.justinkuchmy.customer;
import com.justinkuchmy.customer.Entities.Customer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, Long>{
    Optional<List<Customer>> findByfirstName(String firsName);
    Optional<List<Customer>> findBylastName(String lastName);
    Optional<List<Customer>> findCustomersByCity(String City);
    Optional<List<Customer>> findCustomersByState(String state);
    Optional<Customer> findCustomerByEmail(String Email);
    Optional<Customer> findByCustomerId(Long customerId);
    Long deleteByCustomerId(Long customerId);
}
