package com.sweet.n_plus_one_query.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "order_details")
@AttributeOverride(name = "id", column = @Column(nullable = false))
public class OrderDetailEntity extends BaseEntity {
    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "price", nullable = false, precision = 2)
    private BigDecimal price;

    @Column(name = "order_id")
    private Long orderId;
}