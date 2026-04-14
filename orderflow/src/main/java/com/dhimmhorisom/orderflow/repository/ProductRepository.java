package com.dhimmhorisom.orderflow.repository;

import com.dhimmhorisom.orderflow.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}