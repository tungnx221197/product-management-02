package com.example.productmanagement02.service.impl;

import com.example.productmanagement02.dto.ProductDto;
import com.example.productmanagement02.dto.ProductDto.Request;
import com.example.productmanagement02.dto.ProductDto.Response;
import com.example.productmanagement02.entity.Category;
import com.example.productmanagement02.entity.Product;
import com.example.productmanagement02.exception.DataAlreadyExistsException;
import com.example.productmanagement02.exception.ResourceNotFoundException;
import com.example.productmanagement02.mapper.ProductMapper;
import com.example.productmanagement02.repository.CategoryRepository;
import com.example.productmanagement02.repository.ProductRepository;
import com.example.productmanagement02.service.ProductService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final ProductMapper productMapper;

  @Override
  public Response createProduct(Request productDto) {
    if (productRepository.existsByProductName(productDto.getProductName())) {
      throw new DataAlreadyExistsException(
          "Tên Sản phẩm: " + productDto.getProductName() + " đã " + "tồn tại");
    }

    Category category =
        categoryRepository.findByCategoryName(productDto.getCategoryName())
            .orElseThrow(() -> new ResourceNotFoundException(
                "Danh mục " + productDto.getCategoryName() + " không tồn tại"));

    ProductDto.CategoryInfo categoryInfo = new ProductDto.CategoryInfo();
    categoryInfo.setCategoryId(category.getCategoryId());
    categoryInfo.setCategoryName(category.getCategoryName());

    Product product = productMapper.toProduct(productDto);
    product.setCategory(category);
    Product productSaved = productRepository.save(product);
    ProductDto.Response dto = productMapper.toProductDto(productSaved);
    dto.setCategoryInfo(categoryInfo);
    return dto;
  }

  @Override
  public List<Response> getAllProducts() {
    List<Product> products = productRepository.findAll();
    return products.stream().map(product -> {
      ProductDto.Response productDto = productMapper.toProductDto(product);
      ProductDto.CategoryInfo categoryInfo = new ProductDto.CategoryInfo();
      categoryInfo.setCategoryName(product.getCategory().getCategoryName());
      categoryInfo.setCategoryId(product.getCategory().getCategoryId());
      productDto.setCategoryInfo(categoryInfo);
      return productDto;
    }).collect(Collectors.toList());
  }

  @Override
  public Response getPrductById(Long id) {
    Product product = getProductById(id);
    Category category = getCategoryById(product.getProductId());

    ProductDto.CategoryInfo categoryInfo = new ProductDto.CategoryInfo();
    categoryInfo.setCategoryId(category.getCategoryId());
    categoryInfo.setCategoryName(category.getCategoryName());
    ProductDto.Response dto = productMapper.toProductDto(product);
    dto.setCategoryInfo(categoryInfo);
    return dto;
  }

  @Override
  public Response updateProduct(Long id, Request productDto) {
    Product product = getProductById(id);
    product.setProductName(productDto.getProductName());
    product.setPrice(productDto.getPrice());
    product.setStockQty(productDto.getStockQty());

    String categoryName = product.getCategory().getCategoryName();
    if (!categoryName.equalsIgnoreCase(productDto.getCategoryName())) {
      Category category =
          categoryRepository.findByCategoryName(productDto.getCategoryName())
              .orElseThrow(() -> new ResourceNotFoundException(
                  "Danh mục " + productDto.getCategoryName() + " không tồn tại"));
      product.setCategory(category);
    }

    Product savedProduct = productRepository.save(product);
    Category category = getCategoryById(savedProduct.getCategory().getCategoryId());

    ProductDto.CategoryInfo categoryInfo = new ProductDto.CategoryInfo();
    categoryInfo.setCategoryId(category.getCategoryId());
    categoryInfo.setCategoryName(category.getCategoryName());
    ProductDto.Response dto = productMapper.toProductDto(savedProduct);
    dto.setCategoryInfo(categoryInfo);
    return dto;
  }

  @Override
  public void deleteProductById(Long id) {
    Product product = getProductById(id);
    productRepository.delete(product);
  }

  private Product getProductById(long id) throws ResourceNotFoundException {
    return productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại"));
  }

  private Category getCategoryById(long id) throws ResourceNotFoundException {
    return categoryRepository.findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException("Danh mục không tồn tại!")
        );
  }
}
