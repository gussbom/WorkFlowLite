package com.gusbomcode.workflowlite.mappers;

import com.gusbomcode.workflowlite.dtos.project.requests.CreateProject;
import com.gusbomcode.workflowlite.dtos.api.ApiResponse;
import com.gusbomcode.workflowlite.dtos.project.responses.GetProject;
import com.gusbomcode.workflowlite.dtos.project.responses.ProjectResponse;
import com.gusbomcode.workflowlite.entities.Project;
import com.gusbomcode.workflowlite.enums.ProjectStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ProjectMapper {

    public Project toProjectEntity(CreateProject request){
        return Project.builder()
                .name(request.name())
                .description(request.description())
                .createdAt(Instant.now())
                .status(ProjectStatus.DRAFT)
                .build();
    }

    public ProjectResponse toProjectResponseDto(Project project){
        return ProjectResponse.builder()
                .id(String.valueOf(project.getId()))
                .updatedAt(String.valueOf(project.getUpdatedAt()))
                .createdAt(String.valueOf(project.getCreatedAt()))
                .name(project.getName())
                .description(project.getDescription())
                .build();
    }

    public ApiResponse toApiResponse(ProjectResponse data, HttpStatus status, String message){
        return ApiResponse.<ProjectResponse>builder()
                .timestamp(Instant.now().toString())
                .status(status.value())
                .message(message)
                .data(data)
                .build();
    }

    public GetProject toGetProjectResponse(Project project){
        return GetProject.builder()
                .id(String.valueOf(project.getId()))
                .name(project.getName())
                .description(project.getDescription())
                .status(project.getStatus().name())
                .build();
    }
}
