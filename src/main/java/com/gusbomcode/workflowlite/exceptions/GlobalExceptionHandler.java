package com.gusbomcode.workflowlite.exceptions;

import com.gusbomcode.workflowlite.dtos.api.Api_Error;
import com.gusbomcode.workflowlite.dtos.api.Api_Response;
import com.gusbomcode.workflowlite.exceptions.enums.ErrorCode;
import com.gusbomcode.workflowlite.exceptions.domain.DomainException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Api_Response> handleDomainException(DomainException ex, HttpServletRequest request) {

        ErrorCode errorCode = ex.getErrorCode();

        Api_Error error = Api_Error.builder()
                .code(errorCode.code())
                .message(errorCode.message())
                .status(errorCode.status().value())
                .path(request.getRequestURI())
                .build();

        log.info("Domain error at {}: {}", request.getRequestURI(), ex.getMessage());

        return new ResponseEntity<>(Api_Response.error(error), errorCode.status());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Api_Response> handleEnumError(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {

        String str = ex.getName();
        Api_Error error = Api_Error.builder()
                .code(ErrorCode.INVALID_PARAMETER.code())
                .message(ErrorCode.INVALID_PARAMETER.message() + str)
                .status(ErrorCode.INVALID_PARAMETER.status().value())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(Api_Response.error(error));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Api_Response> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, List<String>> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors().forEach(error ->
//                        errors.put(error.getField(), Collections.singletonList(error.getDefaultMessage()))
                        errors.computeIfAbsent(error.getField(), k -> new ArrayList<>())
                                .add(error.getDefaultMessage())
                );
        Api_Error error = Api_Error.builder()
                .code(ErrorCode.VALIDATION_ERROR.code())
                .message(ErrorCode.VALIDATION_ERROR.message())
                .details(errors)
                .status(ErrorCode.VALIDATION_ERROR.status().value())
                .path(request.getRequestURI())
                .build();

        log.info("Validation failed at {}: {}", request.getRequestURI(), errors);
        return ResponseEntity.badRequest().body(Api_Response.error(error));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Api_Response> handleInvalidEnum(HttpMessageNotReadableException ex, HttpServletRequest request) {

        Api_Error error = Api_Error.builder()
                .code(ErrorCode.INVALID_REQUEST_BODY.code())
                .message(ErrorCode.INVALID_REQUEST_BODY.message())
                .status(ErrorCode.INVALID_REQUEST_BODY.status().value())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(Api_Response.error(error));
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Api_Response> handleUnexpected(HttpServletRequest request) {
//        String traceId = UUID.randomUUID().toString();
//        log.error("Runtime error: [{}] at {} {}", traceId, request.getMethod(), request.getRequestURI());
//
//        Api_Error error = Api_Error.builder()
//                .code(ErrorCode.INTERNAL_ERROR.code())
//                .message(ErrorCode.INTERNAL_ERROR.message())
//                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                .path(request.getRequestURI())
//                .build();
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Api_Response.error(error));
//    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Api_Response> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request) {

        log.warn("Method not supported: {}, path = {}. Supported: {}",

                request.getMethod(),
                request.getRequestURI(),
                ex.getSupportedHttpMethods());

        Api_Error error = Api_Error.builder()
                .status(ex.getStatusCode().value())
                .code(ErrorCode.METHOD_NOT_ALLOWED.name())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(Api_Response.error(error));
    }
}
