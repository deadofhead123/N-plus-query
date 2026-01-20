package com.sweet.n_plus_one_query.service.impl;

import com.sweet.n_plus_one_query.dto.request.OrderRequest;
import com.sweet.n_plus_one_query.entity.OrderEntity;
import com.sweet.n_plus_one_query.exception.DataNotFoundException;
import com.sweet.n_plus_one_query.repository.OrderRepository;
import com.sweet.n_plus_one_query.service.OrderDetailService;
import com.sweet.n_plus_one_query.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public OrderEntity createOrder(OrderRequest orderRequest) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setAddress(orderRequest.getAddress());

        OrderEntity newOrderEntity = orderRepository.save(orderEntity);

        orderDetailService.createOrderDetail(newOrderEntity, orderRequest.getOrderDetails());

        return orderRepository.findById(newOrderEntity.getId()).get();
    }

    @Override
    public OrderEntity getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new DataNotFoundException("Order not found"));
    }

}
