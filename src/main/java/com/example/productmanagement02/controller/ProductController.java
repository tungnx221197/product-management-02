package com.example.productmanagement02.controller;

import com.example.productmanagement02.dto.ProductDto;
import com.example.productmanagement02.dto.ProductDto.Response;
import com.example.productmanagement02.entity.Product;
import com.example.productmanagement02.service.ProductService;
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
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity<ProductDto.Response> createProduct(
      @RequestBody ProductDto.Request productDto) {
    ProductDto.Response product = productService.createProduct(productDto);
    return new ResponseEntity<>(product, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDto.Response> getProductById(
      @PathVariable Long id) {
    ProductDto.Response product = productService.getPrductById(id);
    return ResponseEntity.ok(product);
  }

  @GetMapping
  public ResponseEntity<List<ProductDto.Response>> getAllProducts() {
    List<Response> products = productService.getAllProducts();
    return ResponseEntity.ok(products);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductDto.Response> updateProduct(@PathVariable Long id, @RequestBody ProductDto.Request productDto) {
    ProductDto.Response product = productService.updateProduct(id, productDto);
    return ResponseEntity.ok(product);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long id) {
    productService.deleteProductById(id);
    return ResponseEntity.ok(Collections.singletonMap("message", "Xóa Sản phẩm thành công!"));
  }
}
