package com.gusbomcode.workflowlite.exceptions.domain;

import com.gusbomcode.workflowlite.commons.ErrorCode;

public abstract class DomainException extends RuntimeException{

  private final ErrorCode errorCode;

  protected DomainException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
