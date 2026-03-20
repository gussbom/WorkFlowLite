package com.gusbomcode.workflowlite.commons.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Schema(
        name = "Api_Error",
        description = "Represents error information returned when an API request fails"
)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Api_Error(

        @Schema(
                description = "Application-specific error code",
                example = "PRJ_001"
        )
        String code,

        @Schema(
                description = "Human-readable error message explaining what went wrong",
                example = "Project already exists"
        )
        String message,

        @Schema(
                description = "HTTP status code associated with the error",
                example = "409"
        )
        int status,

        @Schema(
                description = "Request path where the error occurred",
                example = "/api/v1/project/create"
        )
        String path,

        @Schema(
                description = "Detailed validation errors mapped by field name",
                example = """
                {
                  "name": ["must not be blank"],
                  "description": ["must not exceed 255 characters"]
                }
                """
        )
        Map<String, List<String>> details
) {}
