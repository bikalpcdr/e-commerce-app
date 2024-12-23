package com.bikalp.ecommerce.exception.customException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerNotFoundException extends RuntimeException {
    private final String msg;
    public CustomerNotFoundException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
