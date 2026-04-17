package com.dhimmhorisom.orderflow.repository;

import com.dhimmhorisom.orderflow.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);
}