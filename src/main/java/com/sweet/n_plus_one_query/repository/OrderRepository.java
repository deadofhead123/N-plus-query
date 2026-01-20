package com.sweet.n_plus_one_query.repository;

import com.sweet.n_plus_one_query.dto.projection.OrderDtoProjection;
import com.sweet.n_plus_one_query.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query("SELECT new com.sweet.n_plus_one_query.dto.projection.OrderDtoProjection(o.address, oddt.productName, oddt.quantity, oddt.price) " +
            "FROM OrderEntity o LEFT JOIN OrderDetailEntity oddt ON o.id = oddt.orderId " +
            "WHERE o.id = :orderId " )
    List<OrderDtoProjection> findByIdCustom(@Param("orderId") Long orderId);
}
