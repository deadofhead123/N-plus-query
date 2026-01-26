package com.sweet.n_plus_one_query.service;

import com.sweet.n_plus_one_query.dto.request.OrderRequest;
import com.sweet.n_plus_one_query.dto.response.OrderResponse;
import com.sweet.n_plus_one_query.entity.OrderEntity;

public interface OrderService {
    OrderEntity createOrder(OrderRequest orderRequest);
    OrderResponse getOrderById(Long orderId);

    OrderEntity testTransactional(OrderRequest orderRequest);
    OrderEntity testSupportPropagation(OrderRequest orderRequest);
    OrderEntity testNotSupportedPropagation(OrderRequest orderRequest);
}
