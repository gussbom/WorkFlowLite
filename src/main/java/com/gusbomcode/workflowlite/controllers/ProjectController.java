package com.gusbomcode.workflowlite.controllers;

import com.gusbomcode.workflowlite.dtos.project.responses.PageableResponse;
import com.gusbomcode.workflowlite.dtos.project.requests.CreateProject;
import com.gusbomcode.workflowlite.dtos.project.requests.ProjectStatusDto;
import com.gusbomcode.workflowlite.dtos.project.requests.UpdateProject;
import com.gusbomcode.workflowlite.dtos.api.Api_Response;
import com.gusbomcode.workflowlite.dtos.project.responses.ProjectResponse;
import com.gusbomcode.workflowlite.enums.ProjectStatus;
import com.gusbomcode.workflowlite.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="Project Controller",
        description = "All end points/operations related to the life cycle of the Project Entity ")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(
            summary = "Create new project",
            description = "Creates a new project with name and description"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Project Successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api_Response.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400", description = "Validation error/ Invalid input",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api_Response.class)
                    )
            ),
            @ApiResponse(responseCode = "409", description = "Project with the same name already exists",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api_Response.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api_Response.class)
                    )
            )
    })
    @PostMapping("/create")
    public ResponseEntity<Api_Response<ProjectResponse>> createProject(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Project creation payload",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreateProject.class))
            )
            @RequestBody @Valid CreateProject request){
        ProjectResponse response = projectService.createProject(request);
        Api_Response apiResponse = Api_Response.created(response);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update existing project",
            description = "Updates a project with any or all of name, description and status"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api_Response.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Validation failed/Invalid request content",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api_Response.class)
                    )
            ),
            @ApiResponse(responseCode = "409", description = "Project with the same name already exists",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api_Response.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api_Response.class)
                    )
            )
    })
    @PatchMapping("/update/{id}")
    public ResponseEntity<Api_Response<ProjectResponse>> updateProject(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Project update payload",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UpdateProject.class))
            )
            @RequestBody @Valid UpdateProject request,
            @Parameter(
                    description = "Unique project ID",
                    example = "1",
                    required = true
            )
            @PathVariable long id){
        String response = projectService.updateProject(request, id);
        Api_Response apiResponse = Api_Response.success(response);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Fetch project",
            description = "Retrieves a single project data from the db"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api_Response.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api_Response.class)
                    )
            )
    })
    @GetMapping("/fetch/{id}")
    public ResponseEntity<Api_Response<ProjectResponse>> getProject(
            @Parameter(
                    description = "Unique project ID",
                    example = "1",
                    required = true
            )
            @PathVariable long id){
        ProjectResponse response = projectService.getProject(id);
        Api_Response apiResponse = Api_Response.success(response);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @Operation(
            summary = "Fetch all projects",
            description = "Retrieves all projects from db in groups of 10(default). " +
                    "Projects retrieved can also be filtered via their status"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api_Response.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Validation failed/Invalid request content",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api_Response.class)
                    )
            )
    })
    @GetMapping("/fetch-all")
    public ResponseEntity<Api_Response<PageableResponse<ProjectResponse>>> getAllProjects(
            @Parameter(
                    description = "Optional filter to fetch projects by status",
                    example = "DRAFT"
            )
            @RequestParam(required = false)
            @Schema(description = "Filter projects by status", example = "ACTIVE")
            ProjectStatus status,
            @Parameter(
                    description = "Optional filter to view retrieved projects in pages",
                    example = "1"
            )
            @RequestParam(value = "page", required = false,defaultValue = "0")
            @Schema(description = "Set page to view", example = "0")
            int page,
            @Parameter(
                    description = "Optional filter to set the number of projects to  be viewed at a time",
                    example = "3"
            )
            @RequestParam(value = "size",required = false,defaultValue = "10")
            @Schema(description = "Set size of data to views", example = "10")
            int size
            ){
        PageableResponse<ProjectResponse> response = projectService.getAllProjects(status, page, size);
        Api_Response apiResponse = Api_Response.success(response);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Update project status",
            description = "Updates the status of a specific project"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api_Response.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Validation failed/Invalid content",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api_Response.class)
                    )
            )
    })
    @PostMapping("/{id}/status")
    public ResponseEntity<Api_Response<ProjectResponse>> updateStatus(@PathVariable long id, @RequestBody ProjectStatusDto request){
        ProjectResponse response = projectService.updateStatus(id, request);
        Api_Response apiResponse = Api_Response.success(response);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
