package com.gusbomcode.workflowlite.api.dtos.requests;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.gusbomcode.workflowlite.enums.ProjectStatus;

public record ProjectStatusRequest(
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        ProjectStatus status
) {
}
