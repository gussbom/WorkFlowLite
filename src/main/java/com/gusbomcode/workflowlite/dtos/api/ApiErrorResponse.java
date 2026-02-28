package com.gusbomcode.workflowlite.dtos.api;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record ApiErrorResponse(
        String code,
        String message,
        int status,
        String timestamp,
        String path,
        Map<String, List<String>> details
) {
}
