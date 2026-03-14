package com.gusbomcode.workflowlite.service.impl;

import com.gusbomcode.workflowlite.dtos.project.requests.CreateProject;
import com.gusbomcode.workflowlite.dtos.project.requests.UpdateProject;
import com.gusbomcode.workflowlite.dtos.project.responses.ProjectResponse;
import com.gusbomcode.workflowlite.entities.Project;
import com.gusbomcode.workflowlite.enums.ProjectStatus;
import com.gusbomcode.workflowlite.exceptions.domain.ProjectNameExistsException;
import com.gusbomcode.workflowlite.mappers.ProjectMapper;
import com.gusbomcode.workflowlite.repositories.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    ProjectRepository projectRepository;

    @Mock
    ProjectMapper projectMapper;

    @InjectMocks
    ProjectServiceImpl projectService;

    private Project project;
    private Project savedProject;
    private CreateProject createProjectRequest;
    private ProjectResponse projectResponse;
    private UpdateProject updateProjectRequest;

    @BeforeEach
    void setup(){
        project = Project.builder()
                .name("WorkFlowLite")
                .description("Description")
                .build();

        savedProject = Project.builder()
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
                .lastUpdatedAt(Instant.now().toString())
                .build();

        createProjectRequest = CreateProject.builder()
                .name("WorkFlowLite")
                .description("Description")
                .build();

        updateProjectRequest = UpdateProject.builder()
                .description("A fruit")
                .name("Papaya")
                .build();
    }


//    @DisplayName("")
    @Test
    void createProject_returnsProjectResponse_whenProjectIsSaved() {

        when(projectRepository.existsByName(anyString())).thenReturn(Boolean.FALSE);
        when(projectMapper.toProjectEntity(createProjectRequest)).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(savedProject);
        when(projectMapper.toProjectResponse(savedProject)).thenReturn(projectResponse);

        ProjectResponse response = projectService.createProject(createProjectRequest);

        assertNotNull(response);
        assertEquals("WorkFlowLite", response.name());

        verify(projectRepository).existsByName("WorkFlowLite");
        verify(projectRepository).save(project);
        verify(projectMapper).toProjectEntity(createProjectRequest);
        verify(projectMapper).toProjectResponse(savedProject);

        ArgumentCaptor<Project> projectCaptor = ArgumentCaptor.forClass(Project.class);

        verify(projectRepository).save(projectCaptor.capture());

        Project capturedProject = projectCaptor.getValue();

        assertEquals("WorkFlowLite", capturedProject.getName());
        assertEquals("Description", capturedProject.getDescription());
    }

    @Test
    void createProject_shouldThrowException_whenProjectNameExists(){
        when(projectRepository.existsByName(anyString())).thenReturn(true);
        assertThrows(ProjectNameExistsException.class, ()-> {
            projectService.createProject(createProjectRequest);
        });
        verify(projectRepository, never()).save(any());
    }

    @Test
    void updateProject() {

//        when(projectRepository.existsByName(anyString())).thenReturn(false);
//        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
//        when((projectRepository.save(project)).
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