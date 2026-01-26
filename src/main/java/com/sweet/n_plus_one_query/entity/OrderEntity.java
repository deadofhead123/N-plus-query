package com.sweet.n_plus_one_query.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
@AttributeOverride(name = "id", column = @Column(nullable = false))
public class OrderEntity extends BaseEntity {
    @Column(name = "address")
    private String address;
}