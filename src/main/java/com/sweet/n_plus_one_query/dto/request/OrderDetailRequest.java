package com.sweet.n_plus_one_query.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderDetailRequest {
    private String productName;
    private Long quantity;
    private BigDecimal price;
}
