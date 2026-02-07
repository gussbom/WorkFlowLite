package com.gusbomcode.workflowlite.exceptions.domain;

import com.gusbomcode.workflowlite.commons.ErrorCode;

public class ProjectCancelledException extends DomainException {
    public ProjectCancelledException(String message) {
        super(ErrorCode.PROJECT_CANCELLED_EXCEPTION,message);
    }
}
