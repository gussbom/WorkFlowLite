package com.gusbomcode.workflowlite.dtos.project.requests;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.gusbomcode.workflowlite.enums.ProjectStatus;

public record UpdateProjectStatus(
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        ProjectStatus status
) {
}
