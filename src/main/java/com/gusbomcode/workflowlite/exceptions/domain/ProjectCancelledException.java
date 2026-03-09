package com.gusbomcode.workflowlite.exceptions.domain;

import com.gusbomcode.workflowlite.exceptions.enums.ErrorCode;

public class ProjectCancelledException extends DomainException {
    public ProjectCancelledException() {
        super(ErrorCode.PROJECT_CANCELLED_EXCEPTION);
    }
}
