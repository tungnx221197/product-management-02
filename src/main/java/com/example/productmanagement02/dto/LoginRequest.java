package com.example.productmanagement02.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

  private String usernameOrEmail;
  private String password;
}
