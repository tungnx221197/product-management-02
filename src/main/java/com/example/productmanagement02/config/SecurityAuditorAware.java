package com.example.productmanagement02.config;

import com.example.productmanagement02.entity.User;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityAuditorAware implements AuditorAware<User> {

  @Override
  public Optional<User> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(
        authentication.getPrincipal())) {
      return Optional.empty();
    }

    if (authentication.getPrincipal() instanceof User) {
      return Optional.of((User) authentication.getPrincipal());
    }

    return Optional.empty();
  }
}
