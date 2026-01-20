package com.sweet.n_plus_one_query.service.impl;

import com.sweet.n_plus_one_query.dto.projection.OrderDtoProjection;
import com.sweet.n_plus_one_query.dto.request.OrderRequest;
import com.sweet.n_plus_one_query.dto.response.OrderDetailResponse;
import com.sweet.n_plus_one_query.dto.response.OrderResponse;
import com.sweet.n_plus_one_query.entity.OrderEntity;
import com.sweet.n_plus_one_query.exception.DataNotFoundException;
import com.sweet.n_plus_one_query.repository.OrderRepository;
import com.sweet.n_plus_one_query.service.OrderDetailService;
import com.sweet.n_plus_one_query.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public OrderResponse getOrderById(Long orderId) {
        List<OrderDtoProjection> orderDtoProjectionList = orderRepository.findByIdCustom(orderId);

        if(orderDtoProjectionList == null || orderDtoProjectionList.isEmpty()) {
            throw new DataNotFoundException("Order with id = " + orderId + " not found");
        }

        OrderResponse orderResponse = new OrderResponse();

        orderResponse.setAddress(orderDtoProjectionList.get(0).getAddress());
        for(OrderDtoProjection orderDtoProjection : orderDtoProjectionList) {
            orderResponse.getOrderDetailResponseList().add(
                    new OrderDetailResponse(orderDtoProjection.getProductName(),
                                            orderDtoProjection.getQuantity(),
                                            orderDtoProjection.getPrice())
            );
        }

        return orderResponse;
    }

}
