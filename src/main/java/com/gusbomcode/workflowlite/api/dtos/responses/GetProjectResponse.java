package com.gusbomcode.workflowlite.api.dtos.responses;

import lombok.Builder;

@Builder
public record GetProjectResponse(
        String id,
        String name,
        String description,
        String status
) {
}
