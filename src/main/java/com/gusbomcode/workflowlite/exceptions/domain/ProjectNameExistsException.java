package com.gusbomcode.workflowlite.exceptions.domain;

import com.gusbomcode.workflowlite.commons.ErrorCode;

public class ProjectNameExistsException extends DomainException {
    public ProjectNameExistsException(String message) {
        super(ErrorCode.PROJECT_NAME_EXISTS, message);
    }
}
