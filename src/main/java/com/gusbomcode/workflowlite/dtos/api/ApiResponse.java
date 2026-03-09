package com.gusbomcode.workflowlite.dtos.api;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ApiResponse<T>(
        boolean success,
        String timestamp,
        T data,
        ApiError apiError
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                true,
                Instant.now().toString(),
                data,
                null
        );
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(
                true,
                Instant.now().toString(),
                data,
                null
        );
    }

    public static <T> ApiResponse<T> error(ApiError error){
        return new ApiResponse<>(
                false,
                Instant.now().toString(),
                null,
                error
        );
    }
}
