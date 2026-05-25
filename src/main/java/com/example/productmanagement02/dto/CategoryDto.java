package com.example.productmanagement02.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

public class CategoryDto {

  @Getter
  @Setter
  public static class Request {

    @NotBlank(message = "Tên danh mục không được để trống")
    @Size(max = 100, message = "Tên danh mục không được vượt quá 100 ký tự")
    private String categoryName;

    @Size(max = 500, message = "Mô tả không được vượt quá 500 ký tự")
    private String description;
  }

  @Getter
  @Setter
  public static class Response {

    private Long categoryId;
    private String categoryName;
    private String description;
  }
}
