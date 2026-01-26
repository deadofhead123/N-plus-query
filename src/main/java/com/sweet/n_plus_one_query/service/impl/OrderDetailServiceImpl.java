package com.sweet.n_plus_one_query.service.impl;

import com.sweet.n_plus_one_query.dto.request.OrderDetailRequest;
import com.sweet.n_plus_one_query.entity.OrderDetailEntity;
import com.sweet.n_plus_one_query.entity.OrderEntity;
import com.sweet.n_plus_one_query.exception.TransactionalException;
import com.sweet.n_plus_one_query.repository.OrderDetailRepository;
import com.sweet.n_plus_one_query.service.OrderDetailService;
import com.sweet.n_plus_one_query.util.ErrorCode;
import com.sweet.n_plus_one_query.util.LocalizationUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final ModelMapper modelMapper;
    private final LocalizationUtil localizationUtil;

    @Override
    @Transactional
    public void createOrderDetail(OrderEntity orderEntity, List<OrderDetailRequest> orderDetailRequests) {
        List<OrderDetailEntity> orderDetailEntities = new ArrayList<>();

        for (OrderDetailRequest item : orderDetailRequests) {
            OrderDetailEntity newOrderDetailEntity = modelMapper.map(item, OrderDetailEntity.class);
            newOrderDetailEntity.setOrderId(orderEntity.getId());
            orderDetailEntities.add(newOrderDetailEntity);
        }

        orderDetailRepository.saveAll(orderDetailEntities);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void testRequiredNewPropagation() {
        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
        orderDetailEntity.setOrderId(10L);
        orderDetailEntity.setProductName("RPG-7");
        orderDetailEntity.setQuantity(12L);
        orderDetailEntity.setPrice(BigDecimal.valueOf(12));
        orderDetailRepository.save(orderDetailEntity);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void testSupportPropagation() {
        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
        orderDetailEntity.setOrderId(10L);
        orderDetailEntity.setProductName("RPG-7");
        orderDetailEntity.setQuantity(12L);
        orderDetailEntity.setPrice(BigDecimal.valueOf(12));

        if(2 > 3){
            throw new TransactionalException(
                    localizationUtil.getLocalMessage(ErrorCode.Order.TEST_PROPAGATION, "testSupportPropagation", this.getClass().getName())
            );
        }

        orderDetailRepository.save(orderDetailEntity);
    }


}
