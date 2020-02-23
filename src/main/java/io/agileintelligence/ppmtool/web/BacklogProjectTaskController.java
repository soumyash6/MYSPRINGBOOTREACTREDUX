/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.agileintelligence.ppmtool.web;

import io.agileintelligence.ppmtool.domain.ProjectTask;
import io.agileintelligence.ppmtool.repositories.ProjectTaskRepositoryInterface;
import io.agileintelligence.ppmtool.services.MapValidationErrorService;
import io.agileintelligence.ppmtool.services.ProjectTaskService;

import java.security.Principal;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author SOUMYA SAHOO
 */
@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogProjectTaskController {

	private final MapValidationErrorService errorHandel;
	private final ProjectTaskService projectservice;
	private final ProjectTaskRepositoryInterface ptTask;

	@Autowired
	public BacklogProjectTaskController(ProjectTaskService projectservice, MapValidationErrorService errorHandel,
			ProjectTaskRepositoryInterface ptTask) {

		this.errorHandel = errorHandel;
		this.projectservice = projectservice;
		this.ptTask = ptTask;
	}

	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult bindingResult,
			@PathVariable String backlog_id,Principal principal) {

		ResponseEntity<?> errorMap = errorHandel.throughError(bindingResult);
		if (errorMap != null) {
			return errorMap;
		}
		ProjectTask projectTask1 = projectservice.addProjectTask(backlog_id, projectTask,principal.getName());
		return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);

	}

	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id, Principal principalname) {

		return projectservice.findbacklogById(backlog_id, principalname.getName());

	}

	@GetMapping("/{backlog_id}/{project_Seq}")
	public ProjectTask getProjectTask(@PathVariable String backlog_id, @PathVariable String project_Seq) {

		return projectservice.findPtbyProjectsequence(project_Seq, backlog_id);

	}

	@PatchMapping("/{backlog_id}/{project_Seq}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult bindingResult,
			@PathVariable String backlog_id, @PathVariable String project_Seq) {
		ResponseEntity<?> errorMap = errorHandel.throughError(bindingResult);
		if (errorMap != null) {
			return errorMap;
		}
		return new ResponseEntity<ProjectTask>(projectservice.updateProjectTask(projectTask, backlog_id, project_Seq),
				HttpStatus.OK);

	}

	@DeleteMapping("/{backlog_id}/{project_Seq}")
	public ResponseEntity<?> deleteProjecttask(@PathVariable String backlog_id, @PathVariable String project_Seq) {

		return new ResponseEntity<String>(projectservice.deleteprojecttask(backlog_id, project_Seq), HttpStatus.OK);

	}
}
