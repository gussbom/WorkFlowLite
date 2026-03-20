package com.gusbomcode.workflowlite.project.exceptions;

import com.gusbomcode.workflowlite.project.enums.ProjectErrorCodes;

public class ProjectNameExistsExceptionProject extends ProjectDomainException {
    public ProjectNameExistsExceptionProject() {
        super(ProjectErrorCodes.PROJECT_NAME_EXISTS);
    }
}
