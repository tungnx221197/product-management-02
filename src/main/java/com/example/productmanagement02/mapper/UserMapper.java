package com.example.productmanagement02.mapper;

import com.example.productmanagement02.dto.CreateUserRequestDto;
import com.example.productmanagement02.dto.UserResponseDto;
import com.example.productmanagement02.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
  @Mapping(source = "userId", target = "id")
  @Mapping(target = "activeDescription", ignore = true)
  @Mapping(target = "adminDescription", ignore = true)
  UserResponseDto toUserResponseDto(User user);

  @Mapping(target = "password", ignore = true)
  User toUser(CreateUserRequestDto createUserRequestDto);
}
