package com.example.productmanagement02.controller;

import com.example.productmanagement02.dto.CreateUserRequestDto;
import com.example.productmanagement02.dto.LoginRequest;
import com.example.productmanagement02.dto.UserResponseDto;
import com.example.productmanagement02.entity.User;
import com.example.productmanagement02.jwt.JwtUtils;
import com.example.productmanagement02.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;

  // Đăng ký tài khoản
  @PostMapping("/register")
  public ResponseEntity<UserResponseDto> createUser(
      @RequestBody CreateUserRequestDto createUserRequestDto) {
    UserResponseDto userResponseDto = userService.createUser(createUserRequestDto);
    return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
  }

  // kích hoạt tài khoản
  @PatchMapping("/{id}/active")
  public ResponseEntity<UserResponseDto> activeUser(@PathVariable Long id) {
    // 0: Inactive. 1: Active
    UserResponseDto userResponseDto = userService.activeUser(id, 1);
    return ResponseEntity.ok(userResponseDto);
  }

  // khóa tài khoản
  @PatchMapping("/{id}/deactive")
  public ResponseEntity<UserResponseDto> inactiveUser(@PathVariable Long id) {
    // 0: Inactive. 1: Active
    UserResponseDto userResponseDto = userService.activeUser(id, 0);
    return ResponseEntity.ok(userResponseDto);
  }

  // Quản lý quyền Admin (truyền header admin_status: 1 để bật. O để tắt
  @PatchMapping("/{id}/admin")
  public ResponseEntity<UserResponseDto> grantAdmin(@PathVariable Long id,
      @RequestHeader("admin_status") int adminStatus) {
    // 0: không có quyền quản trị viên. 1: Có quyền của quản trị viên
    UserResponseDto userResponseDto = userService.grantUserAdmin(id, adminStatus);
    return ResponseEntity.ok(userResponseDto);
  }

  // Danh sách người dùng
  @GetMapping
  public ResponseEntity<List<UserResponseDto>> getAllUsers() {
    List<UserResponseDto> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    // Thực hiện xác thực thông qua AuthenticationManager
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getUsernameOrEmail(),
            loginRequest.getPassword()
        )
    );

    // Nếu không khớp tài khoản/mật khẩu, dòng trên tự ném lỗi 401 Unauthorized

    // Nếu thành công, bốc User ra để sinh Token
    User user = (User) authentication.getPrincipal();
    String jwtToken = jwtUtils.generateToken(user);

    // Trả Token về cho Client
    Map<String, String> response = new HashMap<>();
    response.put("accessToken", jwtToken);
    response.put("tokenType", "Bearer");

    return ResponseEntity.ok(response);
  }
}
