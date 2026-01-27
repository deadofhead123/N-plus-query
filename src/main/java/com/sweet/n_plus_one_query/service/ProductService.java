package com.sweet.n_plus_one_query.service;

import com.sweet.n_plus_one_query.dto.ProductDto;
import com.sweet.n_plus_one_query.dto.request.ProductRequest;

public interface ProductService {
    ProductDto createProduct(ProductRequest productRequest);
    void createProductWithThread(ProductRequest productRequest) throws InterruptedException;
    void updateStock(Long productId, Long stock) throws InterruptedException;
    Long checkStock(Long productId);
    void fetchStock(Long productId);

}
