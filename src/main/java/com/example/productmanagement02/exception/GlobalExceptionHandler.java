package com.example.productmanagement02.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // 404: Trả về lỗi khi không tìm thấy dữ liệu
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
      HttpServletRequest request) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setTimestamp(LocalDateTime.now());
    errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
    errorResponse.setError("NOT_FOUND");
    errorResponse.setMessage(ex.getMessage());
    errorResponse.setPath(request.getRequestURI());

    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  // 409: Trả về lỗi khi dữ liệu đã tồn tại
  @ExceptionHandler(DataAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleDataAlreadyExistsException(
      DataAlreadyExistsException ex,
      HttpServletRequest request) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setTimestamp(LocalDateTime.now());
    errorResponse.setStatus(HttpStatus.CONFLICT.value());
    errorResponse.setError("CONFLICT");
    errorResponse.setMessage(ex.getMessage());
    errorResponse.setPath(request.getRequestURI());

    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(CustomUsernameNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleCustomUsernameNotFoundException(
      CustomUsernameNotFoundException ex, HttpServletRequest request) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setTimestamp(LocalDateTime.now());
    errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
    errorResponse.setError("UNAUTHORIZED");
    errorResponse.setMessage(ex.getMessage());
    errorResponse.setPath(request.getRequestURI());

    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(UserNotActivatedException.class)
  public ResponseEntity<ErrorResponse> handleUserNotActivatedException(UserNotActivatedException ex,
      HttpServletRequest request) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setTimestamp(LocalDateTime.now());
    errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
    errorResponse.setError("FORBIDDEN");
    errorResponse.setMessage(ex.getMessage());
    errorResponse.setPath(request.getRequestURI());

    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex,
      HttpServletRequest request) {
    ErrorResponse response = new ErrorResponse();
    response.setTimestamp(LocalDateTime.now());
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setError("BAD REQUEST");
    response.setMessage(ex.getMessage());
    response.setPath(request.getRequestURI());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(Exception ex, HttpServletRequest request) {
    ErrorResponse response = new ErrorResponse();
    response.setTimestamp(LocalDateTime.now());
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    response.setError("INTERNAL SERVER ERROR");
    response.setMessage(ex.getMessage());
    response.setPath(request.getRequestURI());
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
      HttpServletRequest request) {

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach((fieldError) -> {
      String fieldName = fieldError.getField();
      String errorMessage = fieldError.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    ErrorResponse response = new ErrorResponse();
    response.setTimestamp(LocalDateTime.now());
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setError("VALIDATION FAILED");
    response.setMessage(errors);
    response.setPath(request.getRequestURI());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex,
      HttpServletRequest request) {
    ErrorResponse response = new ErrorResponse();
    response.setTimestamp(LocalDateTime.now());
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setError("UNAUTHORIZED");
    response.setMessage("Tài khoản hoặc mật khẩu không chính xác!");
    response.setPath(request.getRequestURI());
    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }
}
