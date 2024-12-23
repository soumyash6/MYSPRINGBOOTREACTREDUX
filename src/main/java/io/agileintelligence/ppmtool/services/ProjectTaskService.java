package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.domain.Backlog;
import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.domain.ProjectTask;
import io.agileintelligence.ppmtool.exception.ProjectNotFoundException;
import io.agileintelligence.ppmtool.repositories.BacklogRepositoryInterface;
import io.agileintelligence.ppmtool.repositories.ProjectRepository;
import io.agileintelligence.ppmtool.repositories.ProjectTaskRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    private final ProjectService projectService;
    private final ProjectRepository projectRepository;
    private final BacklogRepositoryInterface backlogRepository;
    private final ProjectTaskRepositoryInterface projectTaskRepository;

    @Autowired
    public ProjectTaskService(ProjectService projectService, ProjectRepository projectRepository,
                              BacklogRepositoryInterface backlogRepository, ProjectTaskRepositoryInterface projectTaskRepository) {
        this.projectService = projectService;
        this.projectRepository = projectRepository;
        this.backlogRepository = backlogRepository;
        this.projectTaskRepository = projectTaskRepository;
    }

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String principalName) {
        try {
            Backlog backlog = projectService.findByProjectIdentifier(projectIdentifier.toUpperCase(), principalName).getBacklog();
            projectTask.setBacklog(backlog);

            Integer backlogSequence = backlog.getPTSequence();
            backlogSequence++;
            backlog.setPTSequence(backlogSequence);

            projectTask.setProjectSequence(projectIdentifier + "_" + backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            if (projectTask.getPriority() == null) {
                projectTask.setPriority(3);
            }

            if (projectTask.getStatus() == null || projectTask.getStatus().isEmpty()) {
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);
        } catch (Exception e) {
            throw new ProjectNotFoundException("Project Not Found");
        }
    }

    public Iterable<ProjectTask> findBacklogById(String id, String principalName) {
        Project project = projectService.findByProjectIdentifier(id, principalName);
        if (project == null) {
            throw new ProjectNotFoundException("Project not found with ID: '" + id + "'");
        }
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String projectSequence, String backlogId, String principalUsername) {
        Project project = projectService.findByProjectIdentifier(backlogId, principalUsername);
        if (project == null) {
            throw new ProjectNotFoundException("Project not found with ID: '" + backlogId + "'");
        }

        ProjectTask projectTask = projectTaskRepository.findByProjectSequenceAndBacklog_ProjectIdentifier(projectSequence, backlogId);
        if (projectTask == null) {
            throw new ProjectNotFoundException("Project Task not found with sequence: '" + projectSequence + "'");
        }

        return projectTask;
    }

    public ProjectTask updateProjectTask(ProjectTask updatedTask, String backlogId, String projectSequence, String principalUsername) {
        ProjectTask projectTask = findPTByProjectSequence(projectSequence, backlogId, principalUsername);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }

    public void deleteProjectTask(String backlogId, String projectSequence, String principalUsername) {
        ProjectTask projectTask = findPTByProjectSequence(projectSequence, backlogId, principalUsername);
        projectTaskRepository.delete(projectTask);
    }
}