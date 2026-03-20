package com.gusbomcode.workflowlite.project.enums;

import org.springframework.http.HttpStatus;

public enum ProjectErrorCodes {

    PROJECT_ALREADY_EXISTS("PRJ_001","Project already exists",HttpStatus.CONFLICT),
    PROJECT_NOT_FOUND("PRJ_002","Project not found",HttpStatus.NOT_FOUND),
    PROJECT_NAME_EXISTS("PRJ_003","Project name already exists",HttpStatus.CONFLICT),
    PROJECT_COMPLETED_EXCEPTION("PRJ_004","Project has been completed",HttpStatus.BAD_REQUEST),
    PROJECT_CANCELLED_EXCEPTION("PRJ_005","Project Has been cancelled",HttpStatus.BAD_REQUEST),
    INVALID_TRANSITION("PRJ_006","Invalid project transition",HttpStatus.BAD_REQUEST),
    INVALID_PARAMETER("PRJ_007","Invalid parameter value for: ",HttpStatus.BAD_REQUEST),
    INVALID_REQUEST_BODY("PRJ_008", "Invalid request data provided", HttpStatus.BAD_REQUEST),


    //    Authentication Errors
    INVALID_BEARER_TOKEN("","",HttpStatus.UNAUTHORIZED),

    //    Validation Errors
    VALIDATION_ERROR("VAL_001","Validation failed",HttpStatus.BAD_REQUEST),

    //    Generic
    INVALID_REQUEST("","",HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR("SYS_001","Internal server error",HttpStatus.INTERNAL_SERVER_ERROR),

    //    Http Errors
    METHOD_NOT_ALLOWED("","",HttpStatus.METHOD_NOT_ALLOWED);

    //    Database Errors

    private final HttpStatus status;
    private final String message;
    private final String code;

    ProjectErrorCodes(String code, String message, HttpStatus status) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public HttpStatus status() {
        return status;
    }
    public String code() { return code;}
    public String message() { return message; }
}
