package com.gusbomcode.workflowlite.project.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(
        name = "PageableResponse",
        description = "Paginated response wrapper containing a list of resources and pagination metadata"
)
@Builder
public record PageableResponse<T>(

        @Schema(description = "List of items returned for the current page")
        List<T> content,
        @Schema(description = "Total number of pages available", example = "5")
        int totalPages,
        @Schema(description = "Current page number (0-based index)", example = "0")
        int pageNo,
        @Schema(description = "Number of items per page", example = "10")
        int pageSize,
        @Schema(description = "Total number of items across all pages", example = "42")
        Long totalElements,
        @Schema(description = "Indicates whether this is the last page", example = "false")
        boolean last
) {}
