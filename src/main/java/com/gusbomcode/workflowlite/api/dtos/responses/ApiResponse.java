package com.gusbomcode.workflowlite.api.dtos.responses;

import lombok.Builder;

@Builder
public record ApiResponse(
        String message,
        int status,
        String timestamp,
        Object data
) {
}
