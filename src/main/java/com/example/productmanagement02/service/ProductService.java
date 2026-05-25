package com.example.productmanagement02.service;

import com.example.productmanagement02.dto.ProductDto;
import com.example.productmanagement02.dto.ProductDto.Response;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProductService {

  ProductDto.Response createProduct(ProductDto.Request productDto);

  List<Response> getAllProducts();

  ProductDto.Response getPrductById(@PathVariable Long id);

  ProductDto.Response updateProduct(@PathVariable Long id,
      @RequestBody ProductDto.Request productDto);

  void deleteProductById(@PathVariable Long id);
}
