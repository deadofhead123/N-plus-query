package com.sweet.n_plus_one_query.repository;

import com.sweet.n_plus_one_query.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByName(String productName);
    ProductEntity findByName(String productName);
}
