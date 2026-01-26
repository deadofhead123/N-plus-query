package com.sweet.n_plus_one_query.service;

import com.sweet.n_plus_one_query.dto.request.OrderDetailRequest;
import com.sweet.n_plus_one_query.entity.OrderEntity;

import java.util.List;

public interface OrderDetailService {
    void createOrderDetail(OrderEntity orderEntity, List<OrderDetailRequest> orderDetailRequests);
    void testRequiredNewPropagation();
    void testSupportPropagation();
}
