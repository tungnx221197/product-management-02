package com.example.productmanagement02.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
  private Long id;
  private String username;
//  private String password;
  private String fullName;
  private String activeDescription;
  private String adminDescription;
}
