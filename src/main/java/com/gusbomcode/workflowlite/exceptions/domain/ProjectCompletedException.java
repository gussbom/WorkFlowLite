package com.gusbomcode.workflowlite.exceptions.domain;

import com.gusbomcode.workflowlite.commons.ErrorCode;

public class ProjectCompletedException extends DomainException {
    public ProjectCompletedException(String message) {
        super(ErrorCode.PROJECT_COMPLETED_EXCEPTION, message);
    }
}
