package com.gusbomcode.workflowlite.api.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateProjectRequest(
        @NotNull
        @Size(max = 50, message="Max size(50) exceeded")
        String name,
        @NotNull
        @Size(max = 500, message="Maximum words(500) exceeded")
        String description
) {
}
