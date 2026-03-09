package com.gusbomcode.workflowlite.dtos.project.responses;

import lombok.Builder;

import java.util.List;

@Builder
public record ProjectsListResponse(
        List<GetProject> projectList
) {
}
