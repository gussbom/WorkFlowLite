package com.gusbomcode.workflowlite.exceptions.domain;

import com.gusbomcode.workflowlite.exceptions.enums.ErrorCode;

public class ProjectNameExistsException extends DomainException {
    public ProjectNameExistsException() {
        super(ErrorCode.PROJECT_NAME_EXISTS);
    }
}
