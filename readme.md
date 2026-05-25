# Product Management System (Core API)

[![Java Version](https://img.shields.io/badge/Java-17%20%2F%2021-orange?style=for-the-badge&logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![Security](https://img.shields.io/badge/Spring%20Security-JWT-blue?style=for-the-badge&logo=springsecurity)](https://spring.io/projects/spring-security)

Hệ thống Web API RESTful quản lý Sản phẩm và Danh mục nội bộ dành cho doanh nghiệp. Dự án được xây dựng dựa trên kiến trúc phân tầng tiêu chuẩn, tập trung vào việc tối ưu hóa hiệu năng ánh xạ dữ liệu, bảo mật không lưu trạng thái (Stateless), tự động hóa kiểm toán lưu vết (Data Auditing) và đồng bộ hóa cấu trúc phản hồi lỗi.

---

## 🚀 Tính Năng Cốt Lõi

- **Authentication & Authorization:** Xác thực người dùng bằng cơ chế Stateless với **JWT Token**. Phân quyền chặt chẽ (`ROLE_USER`, `ROLE_ADMIN`).
- **User Management:** Đăng ký, kích hoạt/khóa tài khoản, cấp quyền quản trị viên linh hoạt.
- **Category & Product CRUD:** Quản lý danh mục và sản phẩm với đầy đủ ràng buộc toàn vẹn dữ liệu.
- **JPA Auditing:** Tự động ghi nhận thông tin lịch sử bản ghi (`CreatedBy`, `CreatedDate`, `UpdatedBy`, `UpdatedDate`) thông qua liên kết ngầm với `SecurityContext`.
- **Data Validation & Transfer:** Sử dụng mô hình **Inner Class DTO** (`Request`/`Response`) kết hợp `jakarta.validation` để kiểm soát dữ liệu đầu vào.
- **Centralized Exception Handling:** Đồng bộ hóa tất cả các lỗi từ tầng Security Filter (401, 403) và tầng Controller về một cấu trúc JSON duy nhất.

---

## 🛠️ Công Nghệ Sử Dụng

- **Backend Core:** Java 17+, Spring Boot 3.x (Spring Framework 6.x)
- **Data Access:** Spring Data JPA, Hibernate, MySQL / Oracle Database
- **Security:** Spring Security, Hoạt động dựa trên thư viện `io.jsonwebtoken (jjwt)`
- **Object Mapping:** MapStruct (Sinh code biên dịch tĩnh, tối ưu CPU/RAM)
- **Tài liệu hóa:** Swagger UI / OpenAPI 3 (Bản 2.8.5+ tương thích Spring Boot mới)
- **Công cụ hỗ trợ:** Lombok, Maven

---

## 📐 Kiến Trúc Thư Mục Dự Án

```text
src/main/java/com/example/productmanagement02/
│
├── config/             # Cấu hình hệ thống (Swagger, JPA Auditing, Security Beans)
├── controller/         # Tầng tiếp nhận Request và điều phối Endpoint API
├── dto/                # Lớp vận chuyển dữ liệu (Inner Class DTO & Error Response)
├── entity/             # Tầng thực thể ánh xạ trực tiếp xuống Database Tables
├── exception/          # Bộ xử lý lỗi tập trung (@RestControllerAdvice, Custom EntryPoint)
├── jwt/                # Tiện ích sinh/xác thực Token và Bộ lọc JwtAuthenticationFilter
├── mapper/             # Các Interface MapStruct phục vụ ánh xạ Entity <-> DTO
├── repository/         # Tầng giao tiếp dữ liệu thô (Spring Data JPA)
├── security/           # Logic nghiệp vụ liên quan đến UserDetails & Core Security
└── service/            # Tầng xử lý logic nghiệp vụ (Interface & Implementations)