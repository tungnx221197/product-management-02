package com.example.productmanagement02.mapper;

import com.example.productmanagement02.dto.ProductDto;
import com.example.productmanagement02.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  Product toProduct(ProductDto.Request productDto);

  @Mapping(source = "productId", target = "id")
  ProductDto.Response toProductDto(Product product);
}
