/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.Exception.ProjectNotFoundException;
import io.agileintelligence.ppmtool.domain.Backlog;
import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.domain.ProjectTask;
import io.agileintelligence.ppmtool.repositories.Backlogrepositoryinterface;
import io.agileintelligence.ppmtool.repositories.ProjectRepository;
import io.agileintelligence.ppmtool.repositories.ProjectTaskRepositoryInterface;
import org.springframework.stereotype.Service;

/**
 *
 * @author SOUMYA SAHOO
 */
@Service
public class ProjectTaskService {

    private final ProjectService ps;
    private final ProjectRepository psr;

    private final Backlogrepositoryinterface blr;

    private final ProjectTaskRepositoryInterface ptri;

    public ProjectTaskService(ProjectService ps, ProjectRepository psr, Backlogrepositoryinterface blr, ProjectTaskRepositoryInterface ptri) {
        this.ps = ps;
        this.psr = psr;
        this.blr = blr;
        this.ptri = ptri;
    }

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask pt) {
        try {
            Backlog backlog = blr.findByprojectIdentifier(projectIdentifier);

            pt.setBacklog(backlog);

            Integer baclogseq = backlog.getPTSequence();

            baclogseq++;

            backlog.setPTSequence(baclogseq);

            pt.setProjectSequence(projectIdentifier + "_" + baclogseq);
            pt.setProjectIdentifier(projectIdentifier);

            if (pt.getPriority() == null) {
                pt.setPriority(3);
            }

            if (pt.getStatus() == null || pt.getStatus() == "") {
                pt.setStatus("TO_DO");
            }
            return ptri.save(pt);
        } catch (Exception e) {
            throw new ProjectNotFoundException("Project Not Found");
        }

    }

    public Iterable<ProjectTask> findbacklogById(String id) {
        Project p = ps.findByProjectIdentifier(id);
        if (p == null) {
            throw new ProjectNotFoundException("Project not found '" + id + "' for this Id");
        }
        return ptri.findByprojectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPtbyProjectsequence(String projectSequence, String backlog_id) {
        Project p = psr.findByProjectIdentifier(backlog_id);
        if (p == null) {
            throw new ProjectNotFoundException("With this id '" + backlog_id + "' project Not Found");
        }

        ProjectTask projectTask = ptri.findByProjectSequenceAndBacklog_projectIdentifier(projectSequence,backlog_id);
            if (projectTask == null) {
            throw new ProjectNotFoundException("With this id '" + projectSequence + "' projectTask Not Found");
        }

        return projectTask;
    }
}
