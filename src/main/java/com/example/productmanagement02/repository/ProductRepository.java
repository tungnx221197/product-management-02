package com.example.productmanagement02.repository;

import com.example.productmanagement02.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Boolean existsByProductName(String productName);
}
