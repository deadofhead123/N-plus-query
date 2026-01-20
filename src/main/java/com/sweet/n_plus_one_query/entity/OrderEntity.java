package com.sweet.n_plus_one_query.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
@AttributeOverride(name = "id", column = @Column(nullable = false))
public class OrderEntity extends BaseEntity {
    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    List<OrderDetailEntity> orderDetails;
}