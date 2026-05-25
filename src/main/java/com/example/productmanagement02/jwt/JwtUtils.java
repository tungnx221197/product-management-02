package com.example.productmanagement02.jwt;

import com.example.productmanagement02.entity.Product;
import com.example.productmanagement02.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

  @Value("${app.jwt-secret}")
  private String jwt_secret;

  // thời gian hết hạn của Token
  private final long jwt_expiration = 86400000L;

  private Key getSingingKey() {
    return Keys.hmacShaKeyFor(jwt_secret.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(User user) {
    Date now = new Date();
    Date expiration = new Date(now.getTime() + jwt_expiration);
    return Jwts.builder().subject(user.getUsername()).claim("userId", user.getUserId())
        .issuedAt(now).expiration(expiration).signWith(getSingingKey()).compact();
  }

  public String getUsernameFromToken(String token) {
    return Jwts.parser()
        .verifyWith((SecretKey) getSingingKey()).build().parseSignedClaims(token).getPayload()
        .getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().verifyWith((SecretKey) getSingingKey()).build().parseSignedClaims(token);
      return true;
    } catch (Exception e) {
      System.out.println("JWT Validation Error: " + e.getMessage());
    }

    return false;
  }
}
