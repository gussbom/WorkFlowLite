package com.gusbomcode.workflowlite.project.exceptions;

import com.gusbomcode.workflowlite.project.enums.ProjectErrorCodes;

public class ProjectCancelledExceptionProject extends ProjectDomainException {
    public ProjectCancelledExceptionProject() {
        super(ProjectErrorCodes.PROJECT_CANCELLED_EXCEPTION);
    }
}
