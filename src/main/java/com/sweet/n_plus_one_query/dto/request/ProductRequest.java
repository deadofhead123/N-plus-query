package com.sweet.n_plus_one_query.dto.request;

import com.sweet.n_plus_one_query.util.ErrorCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    @NotBlank(message = ErrorCode.Product.NAME_NOT_NULL_OR_BLANK)
    private String name;

    private Long quantity;

    @NotNull(message = ErrorCode.Product.PRICE_NOT_NULL_OR_BLANK)
    @Positive(message = ErrorCode.Product.PRICE_MUST_BE_POSITIVE)
    private BigDecimal price;
}
