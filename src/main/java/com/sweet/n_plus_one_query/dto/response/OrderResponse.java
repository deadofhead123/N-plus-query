package com.sweet.n_plus_one_query.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    String address;
    List<OrderDetailResponse> orderDetailResponseList = new ArrayList<>();
}
