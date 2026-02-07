package com.gusbomcode.workflowlite.api.dtos.responses;

import lombok.Builder;

import java.util.List;

@Builder
public record GetAllProjectsResponse(
        List<GetProjectResponse> projectList
) {
}
