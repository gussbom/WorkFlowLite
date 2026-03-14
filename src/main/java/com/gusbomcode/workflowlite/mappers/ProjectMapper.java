package com.gusbomcode.workflowlite.mappers;

import com.gusbomcode.workflowlite.dtos.project.requests.CreateProject;
import com.gusbomcode.workflowlite.dtos.project.responses.ProjectResponse;
import com.gusbomcode.workflowlite.entities.Project;
import com.gusbomcode.workflowlite.enums.ProjectStatus;
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

    public ProjectResponse toProjectResponse(Project project){
        return ProjectResponse.builder()
                .id(String.valueOf(project.getId()))
                .lastUpdatedAt(String.valueOf(project.getUpdatedAt()))
                .createdAt(String.valueOf(project.getCreatedAt()))
                .name(project.getName())
                .status(project.getStatus().name())
                .description(project.getDescription())
                .build();
    }
}
