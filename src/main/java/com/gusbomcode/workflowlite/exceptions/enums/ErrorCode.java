package com.gusbomcode.workflowlite.exceptions.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    PROJECT_ALREADY_EXISTS(HttpStatus.CONFLICT),
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND),
    PROJECT_NAME_EXISTS(HttpStatus.CONFLICT),
    PROJECT_COMPLETED_EXCEPTION(HttpStatus.BAD_REQUEST),
    PROJECT_CANCELLED_EXCEPTION(HttpStatus.BAD_REQUEST),
    INVALID_TRANSITION(HttpStatus.BAD_REQUEST),


    //    Authentication Errors
    INVALID_BEARER_TOKEN(HttpStatus.UNAUTHORIZED),

    //    Validation Errors
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST),

    INVALID_REQUEST(HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),

    //    Http Errors
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED);

    //    Database Errors

    private final HttpStatus status;

    ErrorCode(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
