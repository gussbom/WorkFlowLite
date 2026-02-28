package com.gusbomcode.workflowlite.dtos.project.responses;

import lombok.Builder;

@Builder
public record GetProject(
        String id,
        String name,
        String description,
        String status
) {
}
