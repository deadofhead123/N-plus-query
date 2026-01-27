package com.sweet.n_plus_one_query.service;

import com.sweet.n_plus_one_query.dto.ProductDto;
import com.sweet.n_plus_one_query.dto.request.ProductRequest;
import com.sweet.n_plus_one_query.dto.request.ProductUpdateRequest;
import com.sweet.n_plus_one_query.entity.ProductEntity;

public interface ProductService {
    ProductEntity getProductById(Long productId);
    ProductDto createProduct(ProductRequest productRequest);
    ProductDto updateProduct(Long productId, ProductUpdateRequest productUpdateRequest);
    void createProductWithThread(ProductRequest productRequest) throws InterruptedException;
    void updateStock(Long productId, Long stock) throws InterruptedException;
    Long checkStock(Long productId);
    void fetchStock(Long productId);

    void saveProduct(ProductEntity productEntity);
}
