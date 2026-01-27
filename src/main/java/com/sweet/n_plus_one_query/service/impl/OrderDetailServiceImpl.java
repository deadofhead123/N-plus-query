package com.sweet.n_plus_one_query.service.impl;

import com.sweet.n_plus_one_query.dto.request.OrderDetailRequest;
import com.sweet.n_plus_one_query.entity.OrderDetailEntity;
import com.sweet.n_plus_one_query.entity.OrderEntity;
import com.sweet.n_plus_one_query.entity.ProductEntity;
import com.sweet.n_plus_one_query.exception.NotEnoughQuantityException;
import com.sweet.n_plus_one_query.exception.TransactionalException;
import com.sweet.n_plus_one_query.repository.OrderDetailRepository;
import com.sweet.n_plus_one_query.service.OrderDetailService;
import com.sweet.n_plus_one_query.service.ProductService;
import com.sweet.n_plus_one_query.util.ErrorCode;
import com.sweet.n_plus_one_query.util.LocalizationUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final LocalizationUtil localizationUtil;

    @Override
    @Transactional
    public void createOrderDetail(OrderEntity orderEntity, List<OrderDetailRequest> orderDetailRequests) {
        List<OrderDetailEntity> orderDetailEntities = new ArrayList<>();

        for (OrderDetailRequest item : orderDetailRequests) {
            ProductEntity productEntity = productService.getProductById(item.getProductId());

            if(productEntity.getQuantity() - item.getQuantity() < 0) {
                throw new NotEnoughQuantityException(
                        localizationUtil.getLocalMessage(ErrorCode.Product.PRODUCT_NOT_ENOUGH_QUANTITY, productEntity.getId())
                );
            }

            OrderDetailEntity newOrderDetailEntity = new OrderDetailEntity();
            newOrderDetailEntity.setQuantity(item.getQuantity());
            newOrderDetailEntity.setPrice(productEntity.getPrice());
            newOrderDetailEntity.setProductName(productEntity.getName());
            newOrderDetailEntity.setOrderId(orderEntity.getId());
            newOrderDetailEntity.setProductId(productEntity.getId());

            // Save product after modify quantity
            productEntity.setQuantity(productEntity.getQuantity() - item.getQuantity());
            productService.saveProduct(productEntity);

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
        orderDetailEntity.setPrice(12L);
        orderDetailRepository.save(orderDetailEntity);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void testSupportPropagation() {
        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
        orderDetailEntity.setOrderId(10L);
        orderDetailEntity.setProductName("RPG-7");
        orderDetailEntity.setQuantity(12L);
        orderDetailEntity.setPrice(12L);

        if(2 > 3){
            throw new TransactionalException(
                    localizationUtil.getLocalMessage(ErrorCode.Order.TEST_PROPAGATION, "testSupportPropagation", this.getClass().getName())
            );
        }

        orderDetailRepository.save(orderDetailEntity);
    }


}
