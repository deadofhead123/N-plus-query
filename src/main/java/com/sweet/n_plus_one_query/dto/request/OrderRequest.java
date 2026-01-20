package com.sweet.n_plus_one_query.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    String address;
    List<OrderDetailRequest> orderDetails;
}
