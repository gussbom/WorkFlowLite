package com.gusbomcode.workflowlite.service;

import com.gusbomcode.workflowlite.dtos.project.requests.CreateProject;
import com.gusbomcode.workflowlite.dtos.project.requests.UpdateProjectStatus;
import com.gusbomcode.workflowlite.dtos.project.requests.UpdateProject;
import com.gusbomcode.workflowlite.dtos.project.responses.GetAllProjects;
import com.gusbomcode.workflowlite.dtos.project.responses.GetProject;
import com.gusbomcode.workflowlite.dtos.project.responses.ProjectResponse;

public interface ProjectService {

    ProjectResponse createProject(CreateProject request);

    ProjectResponse updateProject(UpdateProject request, long id);

    GetProject getProject(long id);

    GetAllProjects getAllProjects();

    ProjectResponse updateStatus(long id, UpdateProjectStatus request);
}
