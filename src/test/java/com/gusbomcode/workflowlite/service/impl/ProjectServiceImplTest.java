package com.gusbomcode.workflowlite.service.impl;

import com.gusbomcode.workflowlite.dtos.project.requests.CreateProject;
import com.gusbomcode.workflowlite.dtos.project.responses.ProjectResponse;
import com.gusbomcode.workflowlite.entities.Project;
import com.gusbomcode.workflowlite.enums.ProjectStatus;
import com.gusbomcode.workflowlite.mappers.ProjectMapper;
import com.gusbomcode.workflowlite.repositories.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    ProjectRepository projectRepository;

    @Mock
    ProjectMapper projectMapper;

    @InjectMocks
    ProjectServiceImpl projectService;

    private Project project;
    private CreateProject createProject;
    private ProjectResponse projectResponse;

    @BeforeEach
    void setup(){
        project = Project.builder()
                .id(1L)
                .name("WorkFlowLite")
                .status(ProjectStatus.DRAFT)
                .description("Description")
                .createdAt(Instant.now())
                .build();

        projectResponse = ProjectResponse.builder()
                .id("1")
                .name("WorkFlowLite")
                .description("Description")
                .createdAt(Instant.now().toString())
                .updatedAt(Instant.now().toString())
                .build();

        createProject = CreateProject.builder()
                .name("WorkFlowLite")
                .description("Description")
                .build();
    }


//    @DisplayName("")
    @Test
    void shouldCreateProjectSuccessfully() {

        when(projectRepository.existsByName(createProject.name())).thenReturn(Boolean.FALSE);
        when(projectMapper.toProjectEntity(createProject)).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(project);
        when(projectMapper.toProjectResponseDto(project)).thenReturn(projectResponse);

        ProjectResponse response = projectService.createProject(createProject);

        assertNotNull(response);
        assertEquals("WorkFlowLite", response.name());

        verify(projectRepository).existsByName("WorkFlowLite");
        verify(projectRepository).save(project);
    }

    @Test
    void updateProject() {
    }

    @Test
    void getProject() {
    }

    @Test
    void getAllProjects() {
    }

    @Test
    void updateStatus() {
    }
}