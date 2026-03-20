package com.gusbomcode.workflowlite.project.exceptions;

import com.gusbomcode.workflowlite.project.enums.ProjectErrorCodes;

public abstract class ProjectDomainException extends RuntimeException{

  private final ProjectErrorCodes projectErrorCodes;

  protected ProjectDomainException(ProjectErrorCodes projectErrorCodes) {
    this.projectErrorCodes = projectErrorCodes;
  }

  public ProjectErrorCodes getErrorCode() {
    return projectErrorCodes;
  }
}
