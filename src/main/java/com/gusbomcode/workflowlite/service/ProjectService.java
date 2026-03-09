package com.gusbomcode.workflowlite.service;

import com.gusbomcode.workflowlite.dtos.project.requests.CreateProject;
import com.gusbomcode.workflowlite.dtos.project.requests.ProjectStatusDto;
import com.gusbomcode.workflowlite.dtos.project.requests.UpdateProject;
import com.gusbomcode.workflowlite.dtos.project.responses.ProjectsListResponse;
import com.gusbomcode.workflowlite.dtos.project.responses.GetProject;
import com.gusbomcode.workflowlite.dtos.project.responses.ProjectResponse;
import com.gusbomcode.workflowlite.enums.ProjectStatus;

public interface ProjectService {

    ProjectResponse createProject(CreateProject request);

    ProjectResponse updateProject(UpdateProject request, long id);

    GetProject getProject(long id);

    ProjectsListResponse getAllProjects(ProjectStatus request);

    ProjectResponse updateStatus(long id, ProjectStatusDto request);
}
