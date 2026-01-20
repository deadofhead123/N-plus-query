package com.sweet.n_plus_one_query.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class OrderDetailResponse {
    private String productName;
    private Long quantity;
    private BigDecimal price;
}
