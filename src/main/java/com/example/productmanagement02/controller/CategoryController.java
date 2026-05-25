package com.example.productmanagement02.controller;

import com.example.productmanagement02.dto.CategoryDto;
import com.example.productmanagement02.dto.CategoryDto.Response;
import com.example.productmanagement02.service.CategoryService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping
  public ResponseEntity<CategoryDto.Response> createCategory(
      @RequestBody CategoryDto.Request category) {
    CategoryDto.Response categoryDto = categoryService.createCategory(category);
    return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<Response>> getAllCategories() {
    List<CategoryDto.Response> response = categoryService.getAllCategories();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDto.Response> getCategory(@PathVariable Long id) {
    CategoryDto.Response categoryDto = categoryService.getCategoryById(id);
    return new ResponseEntity<>(categoryDto, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CategoryDto.Response> updateCategory(@PathVariable Long id, @RequestBody CategoryDto.Request category) {
    CategoryDto.Response categoryDto = categoryService.updateCategory(id, category);
    return new ResponseEntity<>(categoryDto, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, String>> deleteCategory(@PathVariable Long id) {
    categoryService.deleteCategory(id);
    return new ResponseEntity<>(Collections.singletonMap("message", "Xóa danh mục thành công!"),
        HttpStatus.OK);
  }
}
