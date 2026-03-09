package com.gusbomcode.workflowlite.service.impl;

import com.gusbomcode.workflowlite.dtos.project.requests.CreateProject;
import com.gusbomcode.workflowlite.dtos.project.requests.ProjectStatusDto;
import com.gusbomcode.workflowlite.dtos.project.requests.UpdateProject;
import com.gusbomcode.workflowlite.dtos.project.responses.ProjectsListResponse;
import com.gusbomcode.workflowlite.dtos.project.responses.GetProject;
import com.gusbomcode.workflowlite.dtos.project.responses.ProjectResponse;
import com.gusbomcode.workflowlite.entities.Project;
import com.gusbomcode.workflowlite.enums.ProjectStatus;
import com.gusbomcode.workflowlite.exceptions.domain.*;
import com.gusbomcode.workflowlite.mappers.ProjectMapper;
import com.gusbomcode.workflowlite.repositories.ProjectRepository;
import com.gusbomcode.workflowlite.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public ProjectResponse createProject(CreateProject request) {
        checkIfNameIsUnique(request.name());
        Project project = projectMapper.toProjectEntity(request);
        Project savedProject = projectRepository.save(project);
        return projectMapper.toProjectResponseDto(savedProject);
    }

    @Transactional
    @Override
    public ProjectResponse updateProject(UpdateProject request, long id) {
        checkIfNameIsUnique(request.name());
        Project project = findProject(id);
        project.updateData(request.name(), request.description());

//        Todo: Work out a strategy to update status alongside.

        Project savedProject = projectRepository.save(project);
        return projectMapper.toProjectResponseDto(savedProject);
    }

    @Override
    public GetProject getProject(long id) {
        Project project = findProject(id);
        return projectMapper.toGetProjectResponse(project);
    }

    @Override
    public ProjectsListResponse getAllProjects(ProjectStatus status) {
        List<GetProject> allFoundProjects = new ArrayList<>();
        List<Project> allProjects;
        if(status==null){
            allProjects = projectRepository.findAll();
        }else{
            allProjects = projectRepository.findAllByStatus(status);
        }
        for(Project p: allProjects){
            GetProject project = projectMapper.toGetProjectResponse(p);
            allFoundProjects.add(project);
        }
        return ProjectsListResponse.builder()
                .projectList(allFoundProjects)
                .build();
    }

    @Transactional
    @Override
    public ProjectResponse updateStatus(long id, ProjectStatusDto requests) {
        Project project = findProject(id);
        switch(requests.status()){
            case DRAFT -> project.draft();
            case ACTIVE -> project.active();
            case COMPLETED -> project.completed();
            case CANCELLED -> project.cancelled();
        }
        Project savedProject = projectRepository.save(project);
        return projectMapper.toProjectResponseDto(savedProject);
    }

    private Project findProject(long id){
        return projectRepository.findById(id)
                .orElseThrow(()->new ProjectNotFoundException());
    }

    private void checkIfNameIsUnique(String name){
        boolean NameIsNotUnique = projectRepository.existsByName(name);
        if(NameIsNotUnique){
            throw new ProjectNameExistsException();
        }
    }
}
