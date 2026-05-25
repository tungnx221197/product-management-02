package com.example.productmanagement02.repository;

import com.example.productmanagement02.entity.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  Boolean existsByCategoryName(String categoryName);
  Optional<Category> findByCategoryName(String categoryName);
}
