package com.bikalp.ecommerce.entity.customer;

import com.bikalp.ecommerce.entity.address.Address;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Document
public class Customer {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;

    public Customer(String id, String firstName, String lastName, String email, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    // Custom Builder
    public static class CustomerBuilder {
        private String id;
        private String firstName;
        private String lastName;
        private String email;
        private Address address;

        public CustomerBuilder id(String id) {
            this.id = id;
            return this;
        }

        public CustomerBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public CustomerBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public CustomerBuilder email(String email) {
            this.email = email;
            return this;
        }

        public CustomerBuilder address(Address address) {
            this.address = address;
            return this;
        }

        public Customer build() {
            return new Customer(id, firstName, lastName, email, address);
        }
    }

    public static CustomerBuilder builder() {
        return new CustomerBuilder();
    }
}