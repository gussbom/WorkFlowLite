package com.gusbomcode.workflowlite.api.dtos.responses;

import lombok.Builder;

@Builder
public record ProjectResponseDto(
        String id,
        String message
) {
}
