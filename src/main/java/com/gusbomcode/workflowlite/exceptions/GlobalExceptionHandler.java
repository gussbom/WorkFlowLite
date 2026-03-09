package com.gusbomcode.workflowlite.exceptions;

import com.gusbomcode.workflowlite.dtos.api.ApiError;
import com.gusbomcode.workflowlite.dtos.api.ApiResponse;
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
    public ResponseEntity<ApiResponse> handleDomainException(DomainException ex, HttpServletRequest request) {

        ErrorCode errorCode = ex.getErrorCode();

        ApiError error = ApiError.builder()
                .code(errorCode.code())
                .message(errorCode.message())
                .status(errorCode.status().value())
                .path(request.getRequestURI())
                .build();

        log.info("Domain error at {}: {}", request.getRequestURI(), ex.getMessage());

        return new ResponseEntity<>(ApiResponse.error(error), errorCode.status());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, List<String>> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors().forEach(error ->
                        errors.put(error.getField(), Collections.singletonList(error.getDefaultMessage()))
                );
        ApiError error = ApiError.builder()
                .code(ErrorCode.VALIDATION_ERROR.code())
                .message(ErrorCode.VALIDATION_ERROR.message())
                .details(errors)
                .status(ErrorCode.VALIDATION_ERROR.status().value())
                .path(request.getRequestURI())
                .build();

        log.info("Validation failed at {}: {}", request.getRequestURI(), errors);
        return ResponseEntity.badRequest().body(ApiResponse.error(error));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleUnexpected(HttpServletRequest request) {
        String traceId = UUID.randomUUID().toString();
        log.error("Runtime error: [{}] at {} {}", traceId, request.getMethod(), request.getRequestURI());

        ApiError error = ApiError.builder()
                .code(ErrorCode.INTERNAL_ERROR.code())
                .message(ErrorCode.INTERNAL_ERROR.message())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(error));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request) {

        log.warn("Method not supported: {}, path = {}. Supported: {}",

                request.getMethod(),
                request.getRequestURI(),
                ex.getSupportedHttpMethods());

        ApiError error = ApiError.builder()
                .status(ex.getStatusCode().value())
                .code(ErrorCode.METHOD_NOT_ALLOWED.name())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ApiResponse.error(error));
    }
}
