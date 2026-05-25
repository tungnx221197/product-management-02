package com.example.productmanagement02.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    final String securitySchemeName = "bearerAuth";

    return new OpenAPI()
        // 1. Cấu hình thông tin chung của dự án
        .info(new Info()
            .title("Product Management API")
            .version("1.0.0")
            .description("Tài liệu hướng dẫn hệ thống Web API quản lý sản phẩm")
            .license(new License().name("Apache 2.0").url("https://springdoc.org")))

        // 2. Thêm yêu cầu bảo mật chung cho toàn bộ các Endpoint (Mặc định bắt buộc có Token)
        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))

        // 3. Định nghĩa cơ chế truyền Token (Bearer JWT) vào Header của Swagger UI
        .components(new Components()
            .addSecuritySchemes(securitySchemeName,
                new SecurityScheme()
                    .name(securitySchemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT") // Hiển thị gợi ý dạng Bearer <Token>
                    .in(SecurityScheme.In.HEADER)
                    .description(
                        "Nhập chuỗi JWT Token của bạn vào đây (Bỏ chữ Bearer, hệ thống tự điền)")));
  }
}
