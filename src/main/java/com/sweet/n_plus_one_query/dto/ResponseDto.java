package com.sweet.n_plus_one_query.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDto {
    String message;
    List<String> error;
    Object data;
}
