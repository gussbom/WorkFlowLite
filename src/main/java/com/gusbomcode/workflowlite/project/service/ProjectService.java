package com.gusbomcode.workflowlite.project.service;

import com.gusbomcode.workflowlite.project.dtos.responses.PageableResponse;
import com.gusbomcode.workflowlite.project.dtos.requests.CreateProject;
import com.gusbomcode.workflowlite.project.dtos.requests.UpdateProject;
import com.gusbomcode.workflowlite.project.dtos.responses.ProjectResponse;
import com.gusbomcode.workflowlite.project.enums.ProjectStatus;

public interface ProjectService {

    ProjectResponse createProject(CreateProject request);

    String updateProject(UpdateProject request, long id);

    ProjectResponse getProject(long id);

    PageableResponse<ProjectResponse> getAllProjects(ProjectStatus request, int page, int size);

    ProjectResponse updateStatus(long id, ProjectStatus request);
}
