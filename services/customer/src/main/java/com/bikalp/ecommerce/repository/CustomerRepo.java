package com.bikalp.ecommerce.repository;

import com.bikalp.ecommerce.entity.customer.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepo extends MongoRepository<Customer, String> {
}
