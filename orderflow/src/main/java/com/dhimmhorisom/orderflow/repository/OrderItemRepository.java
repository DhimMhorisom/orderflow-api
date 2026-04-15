package com.dhimmhorisom.orderflow.repository;

import com.dhimmhorisom.orderflow.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}