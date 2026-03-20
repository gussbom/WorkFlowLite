package com.gusbomcode.workflowlite.project.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "Project response dto", name="Project Response payload")
@Builder
public record ProjectResponse(
        @Schema(description = "Unique project id", example = "1")
        String id,
        @Schema(description = "Unique project name", example = "WorkFlowLite")
        String name,
        @Schema(description = "Project description", example = "A task management project")
        String description,
        @Schema(description = "Project status", example = "CANCELLED")
        String status,
        @Schema(description = "Indicates last time an update was made to project", example = "2026-03-14T21:12:00Z")
        String lastUpdatedAt,
        @Schema(description = "Indicates the time at which a project was created", example = "2026-03-14T21:12:00Z")
        String createdAt
) {
}
