package com.sweet.n_plus_one_query.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearchResponse {
    Long orderId;
    String address;
    String orderProductName;
    String productName;
    Long soldQuantity;
    Long soldPrice;
}
