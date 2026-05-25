package com.example.productmanagement02.exception;

public class UserNotActivatedException extends RuntimeException {

  public UserNotActivatedException(String message) {
    super(message);
  }
}
