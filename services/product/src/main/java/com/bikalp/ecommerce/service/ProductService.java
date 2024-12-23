package com.bikalp.ecommerce.service;

import com.bikalp.ecommerce.exception.customException.ProductPurchaseException;
import com.bikalp.ecommerce.mapper.ProductMapper;
import com.bikalp.ecommerce.repository.ProductRepo;
import com.bikalp.ecommerce.request.ProductPurchaseRequest;
import com.bikalp.ecommerce.request.ProductRequest;
import com.bikalp.ecommerce.response.ProductPurchaseResponse;
import com.bikalp.ecommerce.response.ProductResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService{

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;

    public ProductService(ProductRepo productRepo, ProductMapper productMapper) {
        this.productRepo = productRepo;
        this.productMapper = productMapper;
    }

    public Integer createProduct(ProductRequest productRequest) {
        var product = productMapper.toProduct(productRequest);
        return productRepo.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> purchaseRequest) {
        var productIds = purchaseRequest
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        var storedProducts = productRepo.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products doesn't exists");
        }
        var storesRequest = purchaseRequest
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequests = storesRequest.get(i);
            if (product.getAvailableQuantity() < productRequests.quantity()){
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: "+productRequests.productId());
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequests.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepo.save(product);
            purchasedProducts.add(productMapper.toProductPurchaseResponse(product,productRequests.quantity()));
        }
        return purchasedProducts;
    }

    public ProductResponse findById(Integer productId) {
        return productRepo.findById(productId)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID:: " + productId));
    }

    public List<ProductResponse> findAll() {
        return productRepo.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
