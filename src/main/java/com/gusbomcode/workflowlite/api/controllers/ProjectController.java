package com.gusbomcode.workflowlite.api.controllers;

import com.gusbomcode.workflowlite.api.dtos.requests.CreateProjectRequest;
import com.gusbomcode.workflowlite.api.dtos.requests.ProjectStatusRequest;
import com.gusbomcode.workflowlite.api.dtos.requests.UpdateProjectRequest;
import com.gusbomcode.workflowlite.api.dtos.responses.ApiResponse;
import com.gusbomcode.workflowlite.api.dtos.responses.GetAllProjectsResponse;
import com.gusbomcode.workflowlite.api.dtos.responses.GetProjectResponse;
import com.gusbomcode.workflowlite.api.dtos.responses.ProjectResponseDto;
import com.gusbomcode.workflowlite.enums.ProjectStatus;
import com.gusbomcode.workflowlite.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<ProjectResponseDto> createProject(@RequestBody @Valid CreateProjectRequest request){
        ProjectResponseDto response = projectService.createProject(request);
                return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ProjectResponseDto> updateProject(@RequestBody @Valid UpdateProjectRequest request, @PathVariable long id){
        ProjectResponseDto response = projectService.updateProject(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GetProjectResponse> getProject(@PathVariable long id){
        GetProjectResponse response = projectService.getProject(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<GetAllProjectsResponse> getAllProjects(){
        GetAllProjectsResponse response = projectService.getAllProjects();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<ApiResponse> updateStatus(@PathVariable long id, @RequestBody ProjectStatusRequest request){
        ApiResponse response = projectService.updateStatus(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
