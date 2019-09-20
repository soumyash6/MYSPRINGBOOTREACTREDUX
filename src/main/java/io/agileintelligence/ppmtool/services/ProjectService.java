/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.Exception.ProjectidException;
import io.agileintelligence.ppmtool.domain.Backlog;
import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.repositories.Backlogrepositoryinterface;
import io.agileintelligence.ppmtool.repositories.ProjectRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author SOUMYA SAHOO
 */
@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final Backlogrepositoryinterface backlogrepositoryinterface;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, Backlogrepositoryinterface backlogrepositoryinterface) {
        this.projectRepository = projectRepository;
        this.backlogrepositoryinterface = backlogrepositoryinterface;
    }

    public Project saveOrUpdateProject(Project project) {
        project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
        try {
            if (project.getId() == null) {
                Backlog b = new Backlog();
                project.setBacklog(b);
                b.setProject(project);
                b.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }
            if (project.getId() != null) {
                project.setBacklog(backlogrepositoryinterface.findByprojectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectidException("Project id :" + project.getProjectIdentifier().toUpperCase() + "already exits");
        }

    }

    public Project findByProjectIdentifier(String projectId) {

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectidException("Project " + projectId + " doesn't exits");
        }
        return project;

    }

    public List<Project> findAllProject() {
        return projectRepository.findAll();
    }

    public String deleteByProjectIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectidException("Cann't Delete Project Because " + projectId + " doesn't exits");
        }
        projectRepository.delete(project);
        return "Project With Id" + projectId + "SuccessFully Deleted ";
    }
}
