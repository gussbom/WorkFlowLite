package com.gusbomcode.workflowlite.exceptions.domain;

import com.gusbomcode.workflowlite.exceptions.enums.ErrorCode;

public class ProjectCompletedException extends DomainException {
    public ProjectCompletedException() {
        super(ErrorCode.PROJECT_COMPLETED_EXCEPTION);
    }
}
