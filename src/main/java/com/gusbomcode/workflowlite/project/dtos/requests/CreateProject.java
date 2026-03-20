package com.gusbomcode.workflowlite.project.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;


@Schema(description = "Request to create a project")
@Builder
public record CreateProject(
        @Schema(description = "Project name", example = "WorkflowLite", required = true)
        @NotBlank(message="Project name is blank.")
        @Size(max = 50, message="Max size(50) exceeded")
        String name,

        @Schema(description = "Project description", example = "Task management app", required = true)
        @NotNull
        @Size(max = 500, message="Maximum words(500) exceeded")
        String description
) {
}
