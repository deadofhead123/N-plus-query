package com.sweet.n_plus_one_query.repository;

import com.sweet.n_plus_one_query.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
