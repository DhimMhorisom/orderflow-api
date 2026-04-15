package com.dhimmhorisom.orderflow.repository;

import com.dhimmhorisom.orderflow.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}