package com.gusbomcode.workflowlite.project.exceptions;

import com.gusbomcode.workflowlite.project.enums.ProjectErrorCodes;

public class ProjectCompletedExceptionProject extends ProjectDomainException {
    public ProjectCompletedExceptionProject() {
        super(ProjectErrorCodes.PROJECT_COMPLETED_EXCEPTION);
    }
}
