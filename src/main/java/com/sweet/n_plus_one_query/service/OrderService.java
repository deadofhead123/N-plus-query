package com.sweet.n_plus_one_query.service;

import com.sweet.n_plus_one_query.dto.request.OrderRequest;
import com.sweet.n_plus_one_query.dto.request.OrderSearchRequest;
import com.sweet.n_plus_one_query.dto.response.OrderResponse;
import com.sweet.n_plus_one_query.dto.response.OrderSearchResponse;
import com.sweet.n_plus_one_query.entity.OrderEntity;

import java.util.List;

public interface OrderService {
    OrderEntity createOrder(OrderRequest orderRequest);
    OrderResponse getOrderById(Long orderId);

    OrderEntity testTransactional(OrderRequest orderRequest);
    OrderEntity testSupportPropagation(OrderRequest orderRequest);
    OrderEntity testNotSupportedPropagation(OrderRequest orderRequest);
    List<OrderSearchResponse> getOrderByFilter(OrderSearchRequest orderSearchRequest);
}
