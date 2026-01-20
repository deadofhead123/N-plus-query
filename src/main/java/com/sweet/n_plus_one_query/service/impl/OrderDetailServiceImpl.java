package com.sweet.n_plus_one_query.service.impl;

import com.sweet.n_plus_one_query.dto.request.OrderDetailRequest;
import com.sweet.n_plus_one_query.entity.OrderDetailEntity;
import com.sweet.n_plus_one_query.entity.OrderEntity;
import com.sweet.n_plus_one_query.repository.OrderDetailRepository;
import com.sweet.n_plus_one_query.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void createOrderDetail(OrderEntity orderEntity, List<OrderDetailRequest> orderDetailRequests) {
        List<OrderDetailEntity> orderDetailEntities = new ArrayList<>();

        for (OrderDetailRequest item : orderDetailRequests) {
            OrderDetailEntity newOrderDetailEntity = modelMapper.map(item, OrderDetailEntity.class);
            newOrderDetailEntity.setOrder(orderEntity);
            orderDetailEntities.add(newOrderDetailEntity);
        }

        orderDetailRepository.saveAll(orderDetailEntities);
    }
}
