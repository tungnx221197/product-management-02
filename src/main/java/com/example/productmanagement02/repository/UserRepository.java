package com.example.productmanagement02.repository;

import com.example.productmanagement02.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Boolean existsByEmail(String email);
  Boolean existsByUsername(String username);

  Optional<User> findByUsernameOrEmail(String username, String email);

}
