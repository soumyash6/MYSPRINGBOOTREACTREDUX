/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.exception.ProjectNotFoundException;
import io.agileintelligence.ppmtool.exception.ProjectidException;
import io.agileintelligence.ppmtool.domain.Backlog;
import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.domain.User;
import io.agileintelligence.ppmtool.repositories.Backlogrepositoryinterface;
import io.agileintelligence.ppmtool.repositories.ProjectRepository;
import io.agileintelligence.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author SOUMYA SAHOO
 */
@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final Backlogrepositoryinterface backlogrepositoryinterface;
    private final UserRepository userRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, Backlogrepositoryinterface backlogrepositoryinterface,
                          UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.backlogrepositoryinterface = backlogrepositoryinterface;
        this.userRepository = userRepository;
    }

    public Project saveOrUpdateProject(Project project, String principalname) {
        if (project.getId() != null) {
            Project existingproject2 = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
            if (existingproject2 != null && (!existingproject2.getProjectLeader().equals(principalname))) {
                throw new ProjectNotFoundException("Project not found in Your Account");
            } else if (existingproject2 == null) {
                throw new ProjectNotFoundException("Project with" + project.getProjectIdentifier() + "doesn't exist");
            }
        }
        try {

            User user = userRepository.findByUsername(principalname);

            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if (project.getId() == null) {
                Backlog b = new Backlog();
                project.setBacklog(b);
                b.setProject(project);
                b.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }
            if (project.getId() != null) {
                project.setBacklog(backlogrepositoryinterface
                        .findByprojectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }
            return projectRepository.save(project);
        } catch (Exception e) {
            //e.printStackTrace();
            throw new ProjectidException(
                    "Project id :" + project.getProjectIdentifier().toUpperCase() + "already exits");
        }

    }

    public Project findByProjectIdentifier(String projectId, String princpalname) {

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectidException("Project " + projectId + " doesn't exits");
        }

        if (!project.getProjectLeader().equals(princpalname)) {
            throw new ProjectidException("project is not in ur account");
        }
        return project;

    }

    public List<Project> findAllProject(String name) {
        return projectRepository.findAllByProjectLeader(name);
    }

    public String deleteByProjectIdentifier(String projectId, String principalUserName) {

        projectRepository.delete(findByProjectIdentifier(projectId, principalUserName));
        return "Project With Id" + projectId + "SuccessFully Deleted ";
    }
}
