package com.gusbomcode.workflowlite.dtos.project.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gusbomcode.workflowlite.enums.ProjectStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UpdateProject(
        @NotNull
        @Size(max = 50, message="Max size(50) exceeded")
        String name,
        @NotNull
        @Size(max = 500, message="Maximum words(500) exceeded")
        String description,
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        ProjectStatus status
) {
}
