package com.hcl.producer.handlers;

import com.hcl.producer.exceptions.UserAlreadyExists;
import com.hcl.producer.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<ApiResponse> handleAllOtherException(UserAlreadyExists ex) {
        ApiResponse response = ApiResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.FOUND)
                .success(false)
                .build();
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorMap.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.builder()
                        .data(errorMap)
                        .message(errorMap.toString())
                        .success(false)
                        .status(HttpStatus.BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleAllOtherException(Exception e) {
        ApiResponse response = ApiResponse.builder()
                .message("Something wrong occurred")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .success(false)
                .build();
        return ResponseEntity.internalServerError().body(response);
    }
}
