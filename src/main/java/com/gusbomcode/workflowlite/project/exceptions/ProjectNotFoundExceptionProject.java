package com.gusbomcode.workflowlite.project.exceptions;

import com.gusbomcode.workflowlite.project.enums.ProjectErrorCodes;

public class ProjectNotFoundExceptionProject extends ProjectDomainException {
    public ProjectNotFoundExceptionProject() {
        super(ProjectErrorCodes.PROJECT_NOT_FOUND);
    }
}
