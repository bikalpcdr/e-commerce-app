package com.bikalp.ecommerce.repository;

import com.bikalp.ecommerce.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    List<Product> findAllByIdInOrderById(List<Integer> ids);
}
