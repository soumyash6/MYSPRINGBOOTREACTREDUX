package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.exception.ProjectIdException;
import io.agileintelligence.ppmtool.exception.ProjectNotFoundException;
import io.agileintelligence.ppmtool.entity.Backlog;
import io.agileintelligence.ppmtool.entity.Project;
import io.agileintelligence.ppmtool.entity.User;
import io.agileintelligence.ppmtool.repositories.BacklogRepositoryInterface;
import io.agileintelligence.ppmtool.repositories.ProjectRepository;
import io.agileintelligence.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final BacklogRepositoryInterface backlogRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, BacklogRepositoryInterface backlogRepository,
                          UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.backlogRepository = backlogRepository;
        this.userRepository = userRepository;
    }

    public Project saveOrUpdateProject(Project project, String principalName) {
        if (project.getId() != null) {
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
            if (existingProject != null && (!existingProject.getProjectLeader().equals(principalName))) {
                throw new ProjectNotFoundException("Project not found in Your Account");
            } else if (existingProject == null) {
                throw new ProjectNotFoundException("Project with " + project.getProjectIdentifier() + " doesn't exist");
            }
        }
        try {
            User user = userRepository.findByUsername(principalName);

            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }
            if (project.getId() != null) {
                project.setBacklog(backlogRepository
                        .findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException(
                    "Project id: " + project.getProjectIdentifier().toUpperCase() + " already exists");
        }
    }

    public Project findByProjectIdentifier(String projectId, String principalName) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project " + projectId + " doesn't exist");
        }

        if (!project.getProjectLeader().equals(principalName)) {
            throw new ProjectIdException("Project is not in your account");
        }
        return project;
    }

    public List<Project> findAllProject(String name) {
        return projectRepository.findAllByProjectLeader(name);
    }

    public String deleteByProjectIdentifier(String projectId, String principalUserName) {
        projectRepository.delete(findByProjectIdentifier(projectId, principalUserName));
        return "Project with ID " + projectId + " successfully deleted";
    }
}