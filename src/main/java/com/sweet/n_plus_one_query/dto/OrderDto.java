package com.sweet.n_plus_one_query.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    String address;
    List<OrderDetailDto> orderDetails;
}
