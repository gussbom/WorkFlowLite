package com.gusbomcode.workflowlite.dtos.project.responses;

import lombok.Builder;

@Builder
public record ProjectResponse(
        String id,
        String name,
        String description,
        String updatedAt,
        String createdAt
) {
}
