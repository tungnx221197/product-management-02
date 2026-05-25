package com.example.productmanagement02.service;

import com.example.productmanagement02.dto.CreateUserRequestDto;
import com.example.productmanagement02.dto.UserResponseDto;
import java.util.List;

public interface UserService {

  UserResponseDto createUser(CreateUserRequestDto user);

  UserResponseDto activeUser(Long id, int status);

  UserResponseDto grantUserAdmin(Long id, int status);

  List<UserResponseDto> getAllUsers();
}
