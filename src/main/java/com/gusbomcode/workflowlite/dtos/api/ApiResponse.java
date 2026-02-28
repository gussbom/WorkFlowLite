package com.gusbomcode.workflowlite.dtos.api;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ApiResponse<T>(
        String message,
        int status,
        String timestamp,
        T data
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                "success",
                200,
                Instant.now().toString(),
                data
        );
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(
                "created",
                201,
                Instant.now().toString(),
                data
        );
    }
}
