package com.bikalp.ecommerce.service;

import com.bikalp.ecommerce.entity.customer.Customer;
import com.bikalp.ecommerce.exception.customException.CustomerNotFoundException;
import com.bikalp.ecommerce.mapper.CustomerMapper.CustomerMapper;
import com.bikalp.ecommerce.repository.CustomerRepository.CustomerRepo;
import com.bikalp.ecommerce.request.CustomerRequest;
import com.bikalp.ecommerce.response.CustomerResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final CustomerMapper customerMapper;

    public String createCustomer(@Valid CustomerRequest customerRequest) {
        var customer = customerRepo.save(customerMapper.toCustomer(customerRequest));
        return customer.getId();
    }

    public void updateCustomer(@Valid CustomerRequest customerRequest) {
        var customer = customerRepo.findById(customerRequest.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("Cannot update customer:: No customer found with provided ID:: %s", customerRequest.id())
                ));
        mergeCustomer(customer, customerRequest);
        customerRepo.save(customer);
    }

    private void mergeCustomer(Customer customer, @Valid CustomerRequest customerRequest) {
        if (StringUtils.isNotBlank(customerRequest.firstName())) {
            customer.setFirstName(customerRequest.firstName());
        }
        if (StringUtils.isNotBlank(customerRequest.lastName())) {
            customer.setLastName(customerRequest.lastName());
        }
        if (StringUtils.isNotBlank(customerRequest.email())) {
            customer.setEmail(customerRequest.email());
        }
        if (customerRequest.address() != null) {
            customer.setAddress(customerRequest.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepo.findAll()
                .stream()
                .map(customerMapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean existsById(String customerId) {
        return customerRepo.findById(customerId).isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return customerRepo.findById(customerId)
                .map(customerMapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("No customer found with provided ID:: %s", customerId)
                ));
    }

    public void deleteCustomer(String customerId) {
        customerRepo.deleteById(customerId);
    }
}
