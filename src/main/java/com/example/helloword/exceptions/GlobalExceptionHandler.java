package com.example.helloword.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

// Sử dụng @ControllerAdvice giúp xử lý lỗi trong toàn bộ ứng dụng của mình.
// Nó áp dụng cho tất cả các controllers và bắt các throw ResponseStatusException
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", "error");

        // Kiểm tra nếu ResponseStatusException không có message, cung cấp thông điệp mặc định
        if (ex.getReason() == null || ex.getReason().isEmpty()) {
            body.put("message", "Something went wrong");
        } else {
            body.put("message", ex.getReason());
        }

        return new ResponseEntity<>(body, ex.getStatusCode());
    }
}
