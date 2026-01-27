package com.sweet.n_plus_one_query.dto.projection;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderSearchDtoProjection {
    Long orderId;
    String address;
    String orderProductName;
    String productName;
    Long soldQuantity;
    Long soldPrice;

    public OrderSearchDtoProjection(Long orderId, String address, String orderProductName, String productName, Long soldQuantity, Long soldPrice) {
        this.orderId = orderId;
        this.address = address;
        this.orderProductName = orderProductName;
        this.productName = productName;
        this.soldQuantity = soldQuantity;
        this.soldPrice = soldPrice;
    }
}
