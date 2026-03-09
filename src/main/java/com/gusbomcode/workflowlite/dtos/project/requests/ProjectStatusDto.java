package com.gusbomcode.workflowlite.dtos.project.requests;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.gusbomcode.workflowlite.enums.ProjectStatus;

public record ProjectStatusDto(
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        ProjectStatus status
) {
}
