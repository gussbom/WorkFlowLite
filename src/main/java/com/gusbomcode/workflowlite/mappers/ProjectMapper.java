package com.gusbomcode.workflowlite.mappers;

import com.gusbomcode.workflowlite.api.dtos.requests.CreateProjectRequest;
import com.gusbomcode.workflowlite.api.dtos.requests.ProjectStatusRequest;
import com.gusbomcode.workflowlite.api.dtos.requests.UpdateProjectRequest;
import com.gusbomcode.workflowlite.api.dtos.responses.ApiResponse;
import com.gusbomcode.workflowlite.api.dtos.responses.GetProjectResponse;
import com.gusbomcode.workflowlite.api.dtos.responses.ProjectResponseDto;
import com.gusbomcode.workflowlite.entities.Project;
import com.gusbomcode.workflowlite.enums.ProjectStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ProjectMapper {

    public Project toProjectEntity(CreateProjectRequest request){
        return Project.builder()
                .name(request.name())
                .description(request.description())
                .createdAt(Instant.now())
                .status(ProjectStatus.DRAFT)
                .build();
    }

    public ProjectResponseDto toProjectResponseDto(Project project){
        return ProjectResponseDto.builder()
                .id(String.valueOf(project.getId()))
                .message("Project Created")
                .build();
    }

    public ProjectResponseDto toProjectResponseDto(UpdateProjectRequest request){
        return ProjectResponseDto.builder()
                .message("Project Updated")
                .build();
    }

    public GetProjectResponse toGetProjectResponse(Project project){
        return GetProjectResponse.builder()
                .id(String.valueOf(project.getId()))
                .name(project.getName())
                .description(project.getDescription())
                .status(project.getStatus().name())
                .build();
    }
}
