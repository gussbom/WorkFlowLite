package com.gusbomcode.workflowlite.commons.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;

@Schema(
        name = "Api_Response",
        description = "Standard API response wrapper returned by all endpoints"
)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Api_Response<T>(
        @Schema(description = "Indicates whether the request was successful",
                example = "true")
        boolean success,
        @Schema(description = "Timestamp when the response was generated (ISO-8601 format)",
                example = "2026-03-14T22:35:21Z")
        String timestamp,
        @Schema(description = "Actual response payload containing the requested resource")
        T data,
        @Schema(description = "Error information returned when the request fails")
        Api_Error error
) {

    public static <T> Api_Response<T> success(T data) {
        return new Api_Response<>(
                true,
                Instant.now().toString(),
                data,
                null
        );
    }

    public static <T> Api_Response<T> error(Api_Error error){
        return new Api_Response<>(
                false,
                Instant.now().toString(),
                null,
                error
        );
    }
}

