package com.sweet.n_plus_one_query.service;

import com.sweet.n_plus_one_query.dto.request.OrderRequest;
import com.sweet.n_plus_one_query.entity.OrderEntity;

public interface OrderService {
    OrderEntity createOrder(OrderRequest orderRequest);
    OrderEntity getOrderById(Long orderId);
}
