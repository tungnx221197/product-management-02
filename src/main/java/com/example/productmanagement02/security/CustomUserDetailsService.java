package com.example.productmanagement02.security;

import com.example.productmanagement02.entity.User;
import com.example.productmanagement02.exception.CustomUsernameNotFoundException;
import com.example.productmanagement02.exception.UserNotActivatedException;
import com.example.productmanagement02.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String usernameOrEmail)
      throws CustomUsernameNotFoundException {
    User user =
        userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(
            () -> new CustomUsernameNotFoundException("Tài khoản hoặc email không tồn tại!"));

    if (user.getIsActive() != 1) {
      throw new UserNotActivatedException("Tài khoản chưa được kích hoạt nên không thể đăng nhập");
    }

    return user;
  }
}
