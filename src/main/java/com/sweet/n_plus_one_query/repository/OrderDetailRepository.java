package com.sweet.n_plus_one_query.repository;

import com.sweet.n_plus_one_query.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity,Long> {
}
