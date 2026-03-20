package com.gusbomcode.workflowlite.commons.exception;

import com.gusbomcode.workflowlite.commons.api.Api_Error;
import com.gusbomcode.workflowlite.commons.api.Api_Response;
import com.gusbomcode.workflowlite.project.enums.ProjectErrorCodes;
import com.gusbomcode.workflowlite.project.exceptions.ProjectDomainException;
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

    @ExceptionHandler(ProjectDomainException.class)
    public ResponseEntity<Api_Response> handleDomainException(ProjectDomainException ex, HttpServletRequest request) {

        ProjectErrorCodes projectErrorCodes = ex.getErrorCode();

        Api_Error error = Api_Error.builder()
                .code(projectErrorCodes.code())
                .message(projectErrorCodes.message())
                .status(projectErrorCodes.status().value())
                .path(request.getRequestURI())
                .build();

        log.info("Domain error at {}: {}", request.getRequestURI(), ex.getMessage());

        return new ResponseEntity<>(Api_Response.error(error), projectErrorCodes.status());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Api_Response> handleEnumError(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {

        String str = ex.getName();
        Api_Error error = Api_Error.builder()
                .code(ProjectErrorCodes.INVALID_PARAMETER.code())
                .message(ProjectErrorCodes.INVALID_PARAMETER.message() + str)
                .status(ProjectErrorCodes.INVALID_PARAMETER.status().value())
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
                .code(ProjectErrorCodes.VALIDATION_ERROR.code())
                .message(ProjectErrorCodes.VALIDATION_ERROR.message())
                .details(errors)
                .status(ProjectErrorCodes.VALIDATION_ERROR.status().value())
                .path(request.getRequestURI())
                .build();

        log.info("Validation failed at {}: {}", request.getRequestURI(), errors);
        return ResponseEntity.badRequest().body(Api_Response.error(error));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Api_Response> handleInvalidEnum(HttpMessageNotReadableException ex, HttpServletRequest request) {

        Api_Error error = Api_Error.builder()
                .code(ProjectErrorCodes.INVALID_REQUEST_BODY.code())
                .message(ProjectErrorCodes.INVALID_REQUEST_BODY.message())
                .status(ProjectErrorCodes.INVALID_REQUEST_BODY.status().value())
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
                .code(ProjectErrorCodes.METHOD_NOT_ALLOWED.name())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(Api_Response.error(error));
    }
}
