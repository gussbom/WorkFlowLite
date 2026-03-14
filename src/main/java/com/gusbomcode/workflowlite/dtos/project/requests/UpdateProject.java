package com.gusbomcode.workflowlite.dtos.project.requests;

import com.gusbomcode.workflowlite.enums.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Schema(description = "Request to update an existing project")
@Builder
public record UpdateProject(
        @Schema(description = "Project name", example = "WorkFlowLite")
        @NotNull
        @Size(max = 50, message="Max size(50) exceeded")
        String name,
        @Schema(description = "Project description", example = "Task Management App")
        @NotNull
        @Size(max = 500, message="Maximum words(500) exceeded")
        String description,
        @Schema(description = "Project status", example = "ACTIVE")
        ProjectStatus status
) {
}
