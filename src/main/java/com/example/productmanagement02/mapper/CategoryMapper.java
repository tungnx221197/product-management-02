package com.example.productmanagement02.mapper;

import com.example.productmanagement02.dto.CategoryDto;
import com.example.productmanagement02.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  Category toCategory(CategoryDto.Request categoryDto);

  CategoryDto.Response toCategoryDto(Category category);
}
