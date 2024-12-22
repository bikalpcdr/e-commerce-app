package com.bikalp.ecommerce.response;

import com.bikalp.ecommerce.entity.address.Address;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        Address address
) {
}
