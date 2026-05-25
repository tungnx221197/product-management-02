package com.example.productmanagement02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProductManagement02Application {

  public static void main(String[] args) {
    SpringApplication.run(ProductManagement02Application.class, args);
  }

}
