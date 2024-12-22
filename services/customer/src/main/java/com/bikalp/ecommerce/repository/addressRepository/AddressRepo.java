package com.bikalp.ecommerce.repository.addressRepository;

import com.bikalp.ecommerce.entity.address.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepo extends MongoRepository<Address, String> {
}
