package com.gusbomcode.workflowlite.controllers;

import com.gusbomcode.workflowlite.dtos.project.requests.CreateProject;
import com.gusbomcode.workflowlite.dtos.project.requests.UpdateProjectStatus;
import com.gusbomcode.workflowlite.dtos.project.requests.UpdateProject;
import com.gusbomcode.workflowlite.dtos.api.ApiResponse;
import com.gusbomcode.workflowlite.dtos.project.responses.GetAllProjects;
import com.gusbomcode.workflowlite.dtos.project.responses.GetProject;
import com.gusbomcode.workflowlite.dtos.project.responses.ProjectResponse;
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
    public ResponseEntity<ApiResponse> createProject(@RequestBody @Valid CreateProject request){
        ProjectResponse response = projectService.createProject(request);
        ApiResponse apiResponse = ApiResponse.created(response);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateProject(@RequestBody @Valid UpdateProject request, @PathVariable long id){
        ProjectResponse response = projectService.updateProject(request, id);
        ApiResponse apiResponse = ApiResponse.success(response);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getProject(@PathVariable long id){
        GetProject response = projectService.getProject(id);
        ApiResponse apiResponse = ApiResponse.success(response);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse> getAllProjects(){
        GetAllProjects response = projectService.getAllProjects();
        ApiResponse apiResponse = ApiResponse.success(response);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<ApiResponse> updateStatus(@PathVariable long id, @RequestBody UpdateProjectStatus request){
        ProjectResponse response = projectService.updateStatus(id, request);
        ApiResponse apiResponse = ApiResponse.success(response);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
