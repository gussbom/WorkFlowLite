package com.gusbomcode.workflowlite.exceptions;

import com.gusbomcode.workflowlite.dtos.api.ApiErrorResponse;
import com.gusbomcode.workflowlite.exceptions.enums.ErrorCode;
import com.gusbomcode.workflowlite.exceptions.domain.DomainException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
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

        log.warn("Domain error: {} at {}", ex.getMessage(), request.getRequestURI());

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

        log.warn(" error: {} at {}", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpected(HttpServletRequest request) {
//        String traceId = UUID.randomUUID().toString();
//        log.error("Runtime error: [{}] at {} {}", traceId, request.getMethod(), request.getRequestURI(), ex);

        ApiErrorResponse response = ApiErrorResponse.builder()
                .code(ErrorCode.INTERNAL_ERROR.name())
                .message("An unexpected error occurred")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now().toString())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request) {

        log.warn("Method not supported: {}, path = {}. Supported: {}",

                request.getMethod(),
                request.getRequestURI(),
                ex.getSupportedHttpMethods());

        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(ex.getStatusCode().value())
                .code(ErrorCode.METHOD_NOT_ALLOWED.name())
                .timestamp(Instant.now().toString())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }
}
