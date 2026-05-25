package com.example.productmanagement02.service;

import com.example.productmanagement02.dto.CategoryDto;
import com.example.productmanagement02.entity.Category;
import java.util.List;

public interface CategoryService {

  CategoryDto.Response createCategory(CategoryDto.Request categoryDto);

  List<CategoryDto.Response> getAllCategories();

  CategoryDto.Response getCategoryById(Long id);

  CategoryDto.Response updateCategory(Long id, CategoryDto.Request categoryDto);

  void deleteCategory(Long id);
}
