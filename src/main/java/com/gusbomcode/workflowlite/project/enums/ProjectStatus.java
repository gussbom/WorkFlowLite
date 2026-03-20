package com.gusbomcode.workflowlite.project.enums;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Project status enum")
public enum ProjectStatus {
    DRAFT, ACTIVE, COMPLETED, CANCELLED;

    @com.fasterxml.jackson.annotation.JsonCreator
    public static ProjectStatus from(String value) {
        return ProjectStatus.valueOf(value.trim().toUpperCase());
    }
}
