package com.justinkuchmy.product;
import java.util.Optional;

import org.springframework.stereotype.Repository;


import com.justinkuchmy.product.Entities.Product;

import org.springframework.data.mongodb.repository.MongoRepository;


@Repository
public interface ProductRepository extends MongoRepository<Product, Long>{
    Optional<Product> findProductByProductId(Long Id);
}
