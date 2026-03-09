package com.gusbomcode.workflowlite.dtos.api;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record ApiError(
        String code,
        String message,
        int status,
        String path,
        Map<String, List<String>> details
) {
}
