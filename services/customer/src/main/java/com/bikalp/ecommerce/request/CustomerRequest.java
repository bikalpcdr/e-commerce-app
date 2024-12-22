package com.bikalp.ecommerce.request;

import com.bikalp.ecommerce.entity.address.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,
        @NotNull(message = "Customer firstname is required")
        String firstName,
        @NotNull(message = "Customer lastname is required")
        String lastName,
        @Email(message = "Customer email is required")
        String email,
        Address address
) {
}
