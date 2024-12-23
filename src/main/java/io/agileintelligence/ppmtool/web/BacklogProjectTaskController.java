package io.agileintelligence.ppmtool.web;

import io.agileintelligence.ppmtool.domain.ProjectTask;
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

	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id, Principal principal) {
		return projectTaskService.findBacklogById(backlog_id, principal.getName());
	}

	@GetMapping("/{backlog_id}/{project_Seq}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String project_Seq,
											Principal principal) {
		ProjectTask projectTask = projectTaskService.findPTByProjectSequence(project_Seq, backlog_id, principal.getName());
		return new ResponseEntity<>(projectTask, HttpStatus.OK);
	}

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

	@DeleteMapping("/{backlog_id}/{project_Seq}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String project_Seq,
											   Principal principal) {
		projectTaskService.deleteProjectTask(backlog_id, project_Seq, principal.getName());
		return new ResponseEntity<>("Project Task deleted successfully", HttpStatus.OK);
	}
}