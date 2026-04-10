package com.dhimmhorisom.orderflow.repository;

import com.dhimmhorisom.orderflow.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}