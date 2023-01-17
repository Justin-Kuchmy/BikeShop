package com.portfolioprojects.BikeShop.Sales.Entities.Customer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerRepoTest {
    @Autowired
    private CustomerRepository customerRepository;
    Customers customer = new Customers(
            "first_name",
            "last_name",
            "phone",
            "email",
            "street",
            "city",
            "state",
            "zip_code"
        );
    @Test
	void CustomerRepoTestMethod() {
	}

    @Test
    void ShouldReturnAllCustomers()
    {
        //given
        var customerList = customerRepository.findAll();
        //then
        var size = customerRepository.count();
        //when
        assertEquals(customerList.size(), size);
    }
    @Test
    void ShouldFindCustomerByEmail()
    {
        //given
        Long firstCustomerID = 1L;
        var customerToFindByEmail = customerRepository.findById(firstCustomerID).get().getEmail();
        var result = customerRepository.findCustomerByEmail(customerToFindByEmail);
        //then
        assertNotNull(result.get());
        //when
    }
    @Test
    void ShouldAddNewCustomer()
    {
        //given
        //then
        customerRepository.save(customer);
        var CustomerInDB = customerRepository.findCustomerByEmail(customer.getEmail());
        //when
        assertEquals(customer.getCustomer_id(), CustomerInDB.get().getCustomer_id());

    }
  
    @Test
    void ShouldUpdateCustomersEmail()
    {
        //given
        var StartingCustomer = customerRepository.findCustomerByEmail(customer.getEmail());
        //then
        var EmailToUpdate = "newEmail";
        customerRepository.updatecustomerEmail(Long.valueOf(StartingCustomer.get().getCustomer_id()), EmailToUpdate);
        //when
        assertNotEquals(StartingCustomer.get().getEmail(), EmailToUpdate);
    }
    @Test
    void ShouldDeleteById()
    {
        //given
        Long CustomerIdToDelete = 1446L;
        customerRepository.save(customer); //this customer will be given id 1446 automatically. 
        var StartingCustomer = customerRepository.findById(CustomerIdToDelete);

        //then
        customerRepository.delete(StartingCustomer.get());

        //Optional<customers> will be "Empty" if the customer wasnt found
        var result = customerRepository.findById(Long.valueOf(StartingCustomer.get().getCustomer_id()));
        
        //when
        assertTrue(result.isEmpty());
    }
    

    

   

    

    
}
