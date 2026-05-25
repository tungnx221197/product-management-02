package com.example.productmanagement02.service.impl;

import com.example.productmanagement02.dto.CreateUserRequestDto;
import com.example.productmanagement02.dto.UserResponseDto;
import com.example.productmanagement02.entity.User;
import com.example.productmanagement02.exception.DataAlreadyExistsException;
import com.example.productmanagement02.exception.ResourceNotFoundException;
import com.example.productmanagement02.exception.UserNotActivatedException;
import com.example.productmanagement02.mapper.UserMapper;
import com.example.productmanagement02.repository.UserRepository;
import com.example.productmanagement02.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  private String getActiveDescription(int status) {
    return status == 1 ? "Đã kích hoạt" : "Chưa kích hoạt";
  }

  private String getAdminDescription(int status) {
    return status == 1 ? "Là quản trị viên" : "Không phải là quản trị viên";
  }

  @Override
  public UserResponseDto createUser(CreateUserRequestDto user) {
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new DataAlreadyExistsException("Email đã tồn tại!");
    }

    if (userRepository.existsByUsername(user.getUsername())) {
      throw new DataAlreadyExistsException("Tên đăng nhập đã tồn tại!");
    }

    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    System.out.println(user.getPassword());
    System.out.println(user.getUsername());

    User userSaved = userRepository.save(userMapper.toUser(user));
    UserResponseDto userResponseDto = userMapper.toUserResponseDto(userSaved);
    String activeDescription = getActiveDescription(0);
    String adminDescription = getAdminDescription(0);
    userResponseDto.setActiveDescription(activeDescription);
    userResponseDto.setAdminDescription(adminDescription);
    return userResponseDto;
  }

  @Override
  public UserResponseDto activeUser(Long id, int status) {
    User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
        "Người dùng không tồn tại"));

    user.setIsActive(status);
    if (status == 0) {
      user.setIsAdmin(status);
    }

    User userSaved = userRepository.save(user);
    UserResponseDto userResponseDto = userMapper.toUserResponseDto(userSaved);
    String activeDescription = getActiveDescription(status);
    String adminDescription = getAdminDescription(userSaved.getIsAdmin());
    userResponseDto.setActiveDescription(activeDescription);
    userResponseDto.setAdminDescription(adminDescription);
    return userResponseDto;
  }

  @Override
  public UserResponseDto grantUserAdmin(Long id, int status) {
    User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
        "Người dùng không tồn tại"));

    if (user.getIsActive() == 0) {
      throw new UserNotActivatedException("Tài khoản chưa được kích hoạt nên không thể thêm quyền"
          + " quản trị");
    }

    user.setIsAdmin(status);
    User userSaved = userRepository.save(user);
    UserResponseDto userResponseDto = userMapper.toUserResponseDto(userSaved);
    String activeDescription = getActiveDescription(userSaved.getIsActive());
    String adminDescription = getAdminDescription(status);
    userResponseDto.setActiveDescription(activeDescription);
    userResponseDto.setAdminDescription(adminDescription);
    return userResponseDto;
  }

  @Override
  public List<UserResponseDto> getAllUsers() {
    List<User> users = userRepository.findAll();
    return users.stream().map(user -> {
      UserResponseDto userResponseDto = userMapper.toUserResponseDto(user);
      String activeDescription = getActiveDescription(user.getIsActive());
      String adminDescription = getAdminDescription(user.getIsAdmin());
      userResponseDto.setAdminDescription(adminDescription);
      userResponseDto.setActiveDescription(activeDescription);
      return userResponseDto;
    }).collect(Collectors.toList());
  }

}
