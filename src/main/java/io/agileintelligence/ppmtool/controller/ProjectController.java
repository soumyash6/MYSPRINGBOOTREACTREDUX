package io.agileintelligence.ppmtool.controller;

import io.agileintelligence.ppmtool.entity.Project;
import io.agileintelligence.ppmtool.services.MapValidationErrorService;
import io.agileintelligence.ppmtool.services.ProjectService;

import java.security.Principal;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * ProjectController handles the CRUD operations for Projects.
 */
@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

	private final ProjectService projectService;
	private final MapValidationErrorService errorHandel;

	@Autowired
	public ProjectController(ProjectService projectService, MapValidationErrorService errorHandel) {
		this.projectService = projectService;
		this.errorHandel = errorHandel;
	}

	@Operation(summary = "Create a new project")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Project created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input")
	})
	@PostMapping
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result,
											  Principal principal) {

		ResponseEntity<?> projectError = errorHandel.mapValidationService(result);
		if (projectError != null) {
			return projectError;
		}

		Project projectcreated = projectService.saveOrUpdateProject(project, principal.getName());
		return new ResponseEntity<Project>(project, HttpStatus.CREATED);
	}

	@Operation(summary = "Get a project by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Project retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Project not found")
	})
	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal) {
		Project project = projectService.findByProjectIdentifier(projectId, principal.getName());
		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}

	@Operation(summary = "Get all projects")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Projects retrieved successfully")
	})
	@GetMapping("/All")
	public List<Project> getAllProject(Principal principal) {
		System.out.println(principal.getName());
		return projectService.findAllProject(principal.getName());
	}

	@Operation(summary = "Delete a project by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Project deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Project not found")
	})
	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProject(@PathVariable String projectId, Principal principal) {
		String messAge = projectService.deleteByProjectIdentifier(projectId, principal.getName());
		return new ResponseEntity<String>(messAge, HttpStatus.OK);
	}
}