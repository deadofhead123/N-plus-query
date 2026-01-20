package com.sweet.n_plus_one_query.dto.projection;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDtoProjection {
    String address;
    String productName;
    Long quantity;
    BigDecimal price;
    Long orderId;

    public OrderDtoProjection(String address, String productName, Long quantity, BigDecimal price) {
        this.address = address;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
}
