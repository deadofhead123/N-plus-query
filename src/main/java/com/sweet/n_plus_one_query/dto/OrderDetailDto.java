package com.sweet.n_plus_one_query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class OrderDetailDto {
    private String productName;
    private Long quantity;
    private BigDecimal price;
}
