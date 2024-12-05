package com.youcode.product_managementV2.repository;

import com.youcode.product_managementV2.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
