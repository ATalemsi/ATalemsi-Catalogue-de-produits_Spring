package com.youcode.product_managementV2.repository;

import com.youcode.product_managementV2.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
