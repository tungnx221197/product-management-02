package com.example.productmanagement02.dto;

import com.example.productmanagement02.entity.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ProductDto {

  @Getter
  @Setter
  public static class Request {

    @NotBlank(message = "Tên Sản phẩm không được để trống")
    @Size(max = 200, message = "Tên sản phẩm không được vượt quá 200 ký tự")
    private String productName;

    @NotNull(message = "Giá của sản phẩm không được phép để trống")
    @Min(value = 0, message = "Giá sản phẩm không được nhỏ hơn 0")
    private BigDecimal price;

    @NotNull(message = "Số lượng kho không được để trống")
    @Min(value = 0, message = "Số lượng kho không được nhỏ hơn 0")
    private Integer stockQty;

    @NotNull(message = "Danh mục sản phẩm không được để trống")
    private String categoryName;
  }

  @Getter
  @Setter
  public static class Response {

    private Long id;
    private String productName;
    private BigDecimal price;
    private Integer stockQty;
    private CategoryInfo categoryInfo;
  }

  @Getter
  @Setter
  public static class CategoryInfo {

    private Long categoryId;
    private String categoryName;
  }
}
