package com.gusbomcode.workflowlite.service.impl;

import com.gusbomcode.workflowlite.api.dtos.requests.CreateProjectRequest;
import com.gusbomcode.workflowlite.api.dtos.requests.ProjectStatusRequest;
import com.gusbomcode.workflowlite.api.dtos.requests.UpdateProjectRequest;
import com.gusbomcode.workflowlite.api.dtos.responses.ApiResponse;
import com.gusbomcode.workflowlite.api.dtos.responses.GetAllProjectsResponse;
import com.gusbomcode.workflowlite.api.dtos.responses.GetProjectResponse;
import com.gusbomcode.workflowlite.api.dtos.responses.ProjectResponseDto;
import com.gusbomcode.workflowlite.entities.Project;
import com.gusbomcode.workflowlite.enums.ProjectStatus;
import com.gusbomcode.workflowlite.exceptions.domain.*;
import com.gusbomcode.workflowlite.mappers.ProjectMapper;
import com.gusbomcode.workflowlite.repositories.ProjectRepository;
import com.gusbomcode.workflowlite.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Transactional
    @Override
    public ProjectResponseDto createProject(CreateProjectRequest request) {
        checkIfNameIsUnique(request.name());
        Project project = projectRepository.save(projectMapper.toProjectEntity(request));
        return projectMapper.toProjectResponseDto(project);
    }

    @Transactional
    @Override
    public ProjectResponseDto updateProject(UpdateProjectRequest request, long id) {

        Project project = findProject(id);
        project.updateData(request.name(), request.description());
        checkIfNameIsUnique(request.name());

        projectRepository.save(project);
        return projectMapper.toProjectResponseDto(request);
    }

    @Override
    public GetProjectResponse getProject(long id) {
        Project project = findProject(id);
        return projectMapper.toGetProjectResponse(project);
    }

    @Override
    public GetAllProjectsResponse getAllProjects() {
        List<Project> allProjects = projectRepository.findAll();
        List<GetProjectResponse> allFoundProjects = new ArrayList<>();
        for(Project p: allProjects){
            GetProjectResponse project = projectMapper.toGetProjectResponse(p);
            allFoundProjects.add(project);
        }
        return GetAllProjectsResponse.builder()
                .projectList(allFoundProjects)
                .build();
    }

    @Transactional
    @Override
    public ApiResponse updateStatus(long id, ProjectStatusRequest request) {

        Project project = findProject(id);

        switch(request.status()){
            case ACTIVE -> project.activate();
            case COMPLETED -> project.completed();
            case CANCELLED -> project.cancelled();
        }

        projectRepository.save(project);
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .timestamp(Instant.now().toString())
                .build();
    }

    private Project findProject(long id){
        return projectRepository.findById(id)
                .orElseThrow(()->new ProjectNotFoundException("Project Not Found."));
    }

    private void checkIfNameIsUnique(String name){
        boolean NameIsNotUnique = projectRepository.existsByName(name);
        if(NameIsNotUnique){
            throw new ProjectNameExistsException(
                    "Project Name "+ name +" exists already.");
        }
    }
}
