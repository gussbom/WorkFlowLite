package com.gusbomcode.workflowlite.exceptions.domain;

import com.gusbomcode.workflowlite.commons.ErrorCode;

public class InvalidProjectStatusTransitionException extends DomainException {
    public InvalidProjectStatusTransitionException(String message) {
        super(ErrorCode.INVALID_TRANSITION, message);
    }
}
