package com.gusbomcode.workflowlite.exceptions.domain;

import com.gusbomcode.workflowlite.exceptions.enums.ErrorCode;

public class ProjectNotFoundException extends DomainException {
    public ProjectNotFoundException(String message) {
        super(ErrorCode.PROJECT_NOT_FOUND, message);
    }
}
