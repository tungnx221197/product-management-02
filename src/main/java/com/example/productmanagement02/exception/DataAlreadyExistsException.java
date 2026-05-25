package com.example.productmanagement02.exception;

public class DataAlreadyExistsException extends RuntimeException {

  public DataAlreadyExistsException(String message) {
    super(message);
  }
}
