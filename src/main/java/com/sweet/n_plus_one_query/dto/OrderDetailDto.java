package com.sweet.n_plus_one_query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OrderDetailDto {
    Long productId;
    Long quantity;
}
