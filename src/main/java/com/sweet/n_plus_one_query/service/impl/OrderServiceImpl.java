package com.sweet.n_plus_one_query.service.impl;

import com.sweet.n_plus_one_query.dto.projection.OrderDtoProjection;
import com.sweet.n_plus_one_query.dto.projection.OrderSearchDtoProjection;
import com.sweet.n_plus_one_query.dto.request.OrderRequest;
import com.sweet.n_plus_one_query.dto.request.OrderSearchRequest;
import com.sweet.n_plus_one_query.dto.response.OrderDetailResponse;
import com.sweet.n_plus_one_query.dto.response.OrderResponse;
import com.sweet.n_plus_one_query.dto.response.OrderSearchResponse;
import com.sweet.n_plus_one_query.entity.OrderEntity;
import com.sweet.n_plus_one_query.exception.DataNotFoundException;
import com.sweet.n_plus_one_query.exception.TransactionalException;
import com.sweet.n_plus_one_query.repository.OrderRepository;
import com.sweet.n_plus_one_query.service.AuditService;
import com.sweet.n_plus_one_query.service.OrderDetailService;
import com.sweet.n_plus_one_query.service.OrderService;
import com.sweet.n_plus_one_query.util.ErrorCode;
import com.sweet.n_plus_one_query.util.LocalizationUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;
    private final AuditService auditService;
    private final ModelMapper modelMapper;
    private final LocalizationUtil localizationUtil;

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

    @Override
    @Transactional(rollbackFor = TransactionalException.class, propagation = Propagation.REQUIRES_NEW)
    public OrderEntity testTransactional(OrderRequest orderRequest) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setAddress(orderRequest.getAddress());

        OrderEntity newOrderEntity = orderRepository.save(orderEntity);

        orderDetailService.testRequiredNewPropagation();

        if(orderRequest.getAddress() == null) {
            throw new TransactionalException(
                    localizationUtil.getLocalMessage(ErrorCode.Order.TEST_PROPAGATION, "testTransactional", this.getClass().getName())
            );
        }

        OrderEntity createdOrderEntity = orderRepository.save(orderEntity);

        return orderRepository.findById(createdOrderEntity.getId()).get();
    }

    @Override
    @Transactional
    public OrderEntity testSupportPropagation(OrderRequest orderRequest) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setAddress(orderRequest.getAddress());

        OrderEntity newOrderEntity = orderRepository.save(orderEntity);

        orderDetailService.testSupportPropagation();

        if(orderRequest.getAddress() == null) {
            throw new TransactionalException(
                    localizationUtil.getLocalMessage(ErrorCode.Order.TEST_PROPAGATION, "testSupportPropagation", this.getClass().getName())
            );
        }

        OrderEntity createdOrderEntity = orderRepository.save(orderEntity);

        return orderRepository.findById(createdOrderEntity.getId()).get();
    }

    @Override
    public OrderEntity testNotSupportedPropagation(OrderRequest orderRequest) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setAddress(orderRequest.getAddress());

        OrderEntity newOrderEntity = orderRepository.save(orderEntity);

        auditService.createAudit(this.getClass().getName() + "'s log");

        if(orderRequest.getAddress() == null) {
            throw new TransactionalException("Transaction Failed! Data is rollback!");
        }

        OrderEntity createdOrderEntity = orderRepository.save(orderEntity);

        return orderRepository.findById(createdOrderEntity.getId()).get();
    }

    @Override
    public List<OrderSearchResponse> getOrderByFilter(OrderSearchRequest orderSearchRequest) {
        List<OrderSearchDtoProjection> orderSearchDtoProjections = orderRepository.findByFilter(orderSearchRequest);
        return orderSearchDtoProjections.stream().map(x -> modelMapper.map(x, OrderSearchResponse.class)).toList();
    }
}
