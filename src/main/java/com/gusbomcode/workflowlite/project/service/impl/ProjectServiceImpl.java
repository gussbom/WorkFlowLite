package com.gusbomcode.workflowlite.project.service.impl;

import com.gusbomcode.workflowlite.project.dtos.responses.PageableResponse;
import com.gusbomcode.workflowlite.project.dtos.requests.CreateProject;
import com.gusbomcode.workflowlite.project.dtos.requests.UpdateProject;
import com.gusbomcode.workflowlite.project.dtos.responses.ProjectResponse;
import com.gusbomcode.workflowlite.project.entities.Project;
import com.gusbomcode.workflowlite.project.enums.ProjectStatus;
import com.gusbomcode.workflowlite.project.exceptions.ProjectNameExistsExceptionProject;
import com.gusbomcode.workflowlite.project.exceptions.ProjectNotFoundExceptionProject;
import com.gusbomcode.workflowlite.project.mappers.ProjectMapper;
import com.gusbomcode.workflowlite.project.repositories.ProjectRepository;
import com.gusbomcode.workflowlite.project.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.gusbomcode.workflowlite.project.enums.ProjectStatus.*;

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
        return projectMapper.toProjectResponse(savedProject);
    }

    @Transactional
    @Override
    public String updateProject(UpdateProject request, long id) {
        checkIfNameIsUnique(request.name());
        Project project = findProject(id);
        project.updateData(request.name(), request.description());

        if(request.status() != null){
            project.updateStatus(request.status());
        }
        projectRepository.save(project);
        return "updated";
    }

    @Override
    public ProjectResponse getProject(long id) {
        Project project = findProject(id);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    public PageableResponse<ProjectResponse> getAllProjects(ProjectStatus status, int page, int size) {

        Pageable pageable = PageRequest.of(page,size);
        Page<Project> allProjects;

        if(status==null){
            allProjects = projectRepository.findAll(pageable);
        }else{
            allProjects = projectRepository.findAllByStatus(status, pageable);
        }
        Page<ProjectResponse> mappedPage = allProjects.map(projectMapper::toProjectResponse);
        List<ProjectResponse> allFoundProjects = mappedPage.getContent();

        return PageableResponse.<ProjectResponse>builder()
                .content(allFoundProjects)
                .last(allProjects.isLast())
                .pageNo(allProjects.getNumber())
                .pageSize(allProjects.getSize())
                .totalElements(allProjects.getTotalElements())
                .totalPages(allProjects.getTotalPages())
                .build();
    }

    @Transactional
    @Override
    public ProjectResponse updateStatus(long id, ProjectStatus requests) {
        Project project = findProject(id);
        switch(requests){
            case DRAFT -> project.draft();
            case ACTIVE -> project.active();
            case COMPLETED -> project.completed();
            case CANCELLED -> project.cancelled();
        }
        Project savedProject = projectRepository.save(project);
        return projectMapper.toProjectResponse(savedProject);
    }

    private Project findProject(long id){
        return projectRepository.findById(id)
                .orElseThrow(()->new ProjectNotFoundExceptionProject());
    }

    private void checkIfNameIsUnique(String name){
        boolean NameIsNotUnique = projectRepository.existsByName(name);
        if(NameIsNotUnique){
            throw new ProjectNameExistsExceptionProject();
        }
    }
}
