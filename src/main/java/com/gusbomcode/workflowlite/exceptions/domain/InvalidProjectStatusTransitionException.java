package com.gusbomcode.workflowlite.exceptions.domain;

import com.gusbomcode.workflowlite.exceptions.enums.ErrorCode;

public class InvalidProjectStatusTransitionException extends DomainException {
    public InvalidProjectStatusTransitionException() {
        super(ErrorCode.INVALID_TRANSITION);
    }
}
