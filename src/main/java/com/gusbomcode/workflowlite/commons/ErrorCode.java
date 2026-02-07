package com.gusbomcode.workflowlite.commons;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    PROJECT_ALREADY_EXISTS(HttpStatus.CONFLICT),
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND),
    PROJECT_NAME_EXISTS(HttpStatus.CONFLICT),
    PROJECT_COMPLETED_EXCEPTION(HttpStatus.BAD_REQUEST),
    PROJECT_CANCELLED_EXCEPTION(HttpStatus.BAD_REQUEST),
    INVALID_TRANSITION(HttpStatus.BAD_REQUEST),


    //    Authentication
    INVALID_BEARER_TOKEN(HttpStatus.UNAUTHORIZED),

    //    Validation
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST),

    INVALID_REQUEST(HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);


    private final HttpStatus status;

    ErrorCode(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
