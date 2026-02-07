package com.gusbomcode.workflowlite.service;

import com.gusbomcode.workflowlite.api.dtos.requests.CreateProjectRequest;
import com.gusbomcode.workflowlite.api.dtos.requests.ProjectStatusRequest;
import com.gusbomcode.workflowlite.api.dtos.requests.UpdateProjectRequest;
import com.gusbomcode.workflowlite.api.dtos.responses.ApiResponse;
import com.gusbomcode.workflowlite.api.dtos.responses.GetAllProjectsResponse;
import com.gusbomcode.workflowlite.api.dtos.responses.GetProjectResponse;
import com.gusbomcode.workflowlite.api.dtos.responses.ProjectResponseDto;
import com.gusbomcode.workflowlite.entities.Project;

public interface ProjectService {

    ProjectResponseDto createProject(CreateProjectRequest request);

    ProjectResponseDto updateProject(UpdateProjectRequest request, long id);

    GetProjectResponse getProject(long id);

    GetAllProjectsResponse getAllProjects();

    ApiResponse updateStatus(long id, ProjectStatusRequest request);
}
