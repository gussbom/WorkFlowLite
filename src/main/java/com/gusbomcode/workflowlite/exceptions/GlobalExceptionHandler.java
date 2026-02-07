package com.gusbomcode.workflowlite.exceptions;

import com.gusbomcode.workflowlite.commons.ApiErrorResponse;
import com.gusbomcode.workflowlite.commons.ErrorCode;
import com.gusbomcode.workflowlite.exceptions.domain.DomainException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiErrorResponse> handleDomainException(DomainException ex, HttpServletRequest request) {

        ErrorCode errorCode = ex.getErrorCode();

        ApiErrorResponse response = ApiErrorResponse.builder()
                .code(errorCode.name())
                .message(ex.getMessage())
                .status(errorCode.getStatus().value())
                .timestamp(LocalDateTime.now().toString())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, List<String>> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors().forEach(error ->
                        errors.put(error.getField(), Collections.singletonList(error.getDefaultMessage()))
                );
        ApiErrorResponse response = ApiErrorResponse.builder()
                .code(ErrorCode.VALIDATION_ERROR.name())
                .message("Validation Failed")
                .details(errors)
                .status(ErrorCode.VALIDATION_ERROR.getStatus().value())
                .timestamp(LocalDateTime.now().toString())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpected(Exception ex, HttpServletRequest request) {

        ApiErrorResponse response = ApiErrorResponse.builder()
                .code(ErrorCode.INTERNAL_ERROR.name())
                .message("An unexpected error occurred")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now().toString())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
