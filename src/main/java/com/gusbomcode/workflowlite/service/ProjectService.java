package com.gusbomcode.workflowlite.service;

import com.gusbomcode.workflowlite.dtos.project.responses.PageableResponse;
import com.gusbomcode.workflowlite.dtos.project.requests.CreateProject;
import com.gusbomcode.workflowlite.dtos.project.requests.ProjectStatusDto;
import com.gusbomcode.workflowlite.dtos.project.requests.UpdateProject;
import com.gusbomcode.workflowlite.dtos.project.responses.ProjectResponse;
import com.gusbomcode.workflowlite.enums.ProjectStatus;

public interface ProjectService {

    ProjectResponse createProject(CreateProject request);

    String updateProject(UpdateProject request, long id);

    ProjectResponse getProject(long id);

    PageableResponse<ProjectResponse> getAllProjects(ProjectStatus request, int page, int size);

    ProjectResponse updateStatus(long id, ProjectStatusDto request);
}
