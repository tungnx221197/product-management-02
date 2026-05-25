package com.example.productmanagement02.service.impl;

import com.example.productmanagement02.dto.CategoryDto;
import com.example.productmanagement02.dto.CategoryDto.Request;
import com.example.productmanagement02.dto.CategoryDto.Response;
import com.example.productmanagement02.entity.Category;
import com.example.productmanagement02.exception.DataAlreadyExistsException;
import com.example.productmanagement02.exception.ResourceNotFoundException;
import com.example.productmanagement02.mapper.CategoryMapper;
import com.example.productmanagement02.repository.CategoryRepository;
import com.example.productmanagement02.service.CategoryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository repository;
  private final CategoryMapper mapper;

  @Override
  public Response createCategory(Request categoryDto) {
    if (repository.existsByCategoryName(categoryDto.getCategoryName())) {
      throw new DataAlreadyExistsException("Danh mục: " + categoryDto.getCategoryName() + " đã "
          + "tồn tại");
    }

    Category savedCategory = repository.save(mapper.toCategory(categoryDto));
    return mapper.toCategoryDto(savedCategory);
  }

  @Override
  public List<Response> getAllCategories() {
    List<Category> categories = repository.findAll();
    return categories.stream().map(mapper::toCategoryDto).collect(Collectors.toList());
  }

  @Override
  public Response getCategoryById(Long id) {
    Category category = getCategory(id);

    return mapper.toCategoryDto(category);
  }

  @Override
  public Response updateCategory(Long id, Request categoryDto) {
    Category category = getCategory(id);

    String categoryName = categoryDto.getCategoryName();
    if (repository.existsByCategoryName(categoryName) && !category.getCategoryName()
        .equals(categoryName)) {
      throw new DataAlreadyExistsException("Danh mục: " + categoryDto.getCategoryName() + " đã "
          + "tồn tại");
    }

    category.setCategoryName(categoryName);
    category.setDescription(categoryDto.getDescription());
    Category updatedCategory = repository.save(category);
    return mapper.toCategoryDto(updatedCategory);
  }

  @Override
  public void deleteCategory(Long id) {
    Category category = getCategory(id);
    repository.delete(category);
  }

  private Category getCategory(long id) throws ResourceNotFoundException {
    return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
        "Danh mục không tồn tại!"));
  }
}
