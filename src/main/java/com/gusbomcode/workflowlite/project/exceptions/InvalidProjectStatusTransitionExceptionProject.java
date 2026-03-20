package com.gusbomcode.workflowlite.project.exceptions;

import com.gusbomcode.workflowlite.project.enums.ProjectErrorCodes;

public class InvalidProjectStatusTransitionExceptionProject extends ProjectDomainException {
    public InvalidProjectStatusTransitionExceptionProject() {
        super(ProjectErrorCodes.INVALID_TRANSITION);
    }
}
