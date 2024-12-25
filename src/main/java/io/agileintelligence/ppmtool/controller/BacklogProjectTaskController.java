package io.agileintelligence.ppmtool.controller;

import io.agileintelligence.ppmtool.entity.ProjectTask;
import io.agileintelligence.ppmtool.repositories.ProjectTaskRepositoryInterface;
import io.agileintelligence.ppmtool.services.MapValidationErrorService;
import io.agileintelligence.ppmtool.services.ProjectTaskService;

import java.security.Principal;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * BacklogProjectTaskController handles the CRUD operations for Project Tasks in the backlog.
 */
@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogProjectTaskController {

    private final MapValidationErrorService mapValidationErrorService;
    private final ProjectTaskService projectTaskService;
    private final ProjectTaskRepositoryInterface projectTaskRepository;

    @Autowired
    public BacklogProjectTaskController(ProjectTaskService projectTaskService, MapValidationErrorService mapValidationErrorService,
                                        ProjectTaskRepositoryInterface projectTaskRepository) {
        this.mapValidationErrorService = mapValidationErrorService;
        this.projectTaskService = projectTaskService;
        this.projectTaskRepository = projectTaskRepository;
    }

    @Operation(summary = "Add a new project task to the backlog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project task created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult bindingResult,
                                            @PathVariable String backlog_id, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(bindingResult);
        if (errorMap != null) {
            return errorMap;
        }
        ProjectTask newProjectTask = projectTaskService.addProjectTask(backlog_id, projectTask, principal.getName());
        return new ResponseEntity<>(newProjectTask, HttpStatus.CREATED);
    }

    @Operation(summary = "Get the backlog of project tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Backlog retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Backlog not found")
    })
    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id, Principal principal) {
        return projectTaskService.findBacklogById(backlog_id, principal.getName());
    }

    @Operation(summary = "Get a specific project task by its sequence")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project task retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Project task not found")
    })
    @GetMapping("/{backlog_id}/{project_Seq}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String project_Seq,
                                            Principal principal) {
        ProjectTask projectTask = projectTaskService.findPTByProjectSequence(project_Seq, backlog_id, principal.getName());
        return new ResponseEntity<>(projectTask, HttpStatus.OK);
    }

    @Operation(summary = "Update a project task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project task updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PatchMapping("/{backlog_id}/{project_Seq}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult bindingResult,
                                               @PathVariable String backlog_id, @PathVariable String project_Seq, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(bindingResult);
        if (errorMap != null) {
            return errorMap;
        }
        ProjectTask updatedProjectTask = projectTaskService.updateProjectTask(projectTask, backlog_id, project_Seq, principal.getName());
        return new ResponseEntity<>(updatedProjectTask, HttpStatus.OK);
    }

    @Operation(summary = "Delete a project task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Project task not found")
    })
    @DeleteMapping("/{backlog_id}/{project_Seq}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String project_Seq,
                                               Principal principal) {
        projectTaskService.deleteProjectTask(backlog_id, project_Seq, principal.getName());
        return new ResponseEntity<>("Project Task deleted successfully", HttpStatus.OK);
    }
}