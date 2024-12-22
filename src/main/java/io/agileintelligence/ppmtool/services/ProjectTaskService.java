/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.exception.ProjectNotFoundException;
import io.agileintelligence.ppmtool.domain.Backlog;
import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.domain.ProjectTask;
import io.agileintelligence.ppmtool.repositories.Backlogrepositoryinterface;
import io.agileintelligence.ppmtool.repositories.ProjectRepository;
import io.agileintelligence.ppmtool.repositories.ProjectTaskRepositoryInterface;
import org.springframework.stereotype.Service;

/**
 * @author SOUMYA SAHOO
 */
@Service
public class ProjectTaskService {

    private final ProjectService ps;
    private final ProjectRepository psr;

    private final Backlogrepositoryinterface blr;

    private final ProjectTaskRepositoryInterface ptri;

    public ProjectTaskService(ProjectService ps, ProjectRepository psr, Backlogrepositoryinterface blr,
                              ProjectTaskRepositoryInterface ptri) {
        this.ps = ps;
        this.psr = psr;
        this.blr = blr;
        this.ptri = ptri;
    }

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask pt, String pricipalName) {
        try {
            Backlog backlog = ps.findByProjectIdentifier(projectIdentifier.toUpperCase(), pricipalName).getBacklog();// blr.findByprojectIdentifier(projectIdentifier);

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

    public Iterable<ProjectTask> findbacklogById(String id, String principalname) {
        Project p = ps.findByProjectIdentifier(id, principalname);
        if (p == null) {
            throw new ProjectNotFoundException("Project not found '" + id + "' for this Id");
        }
        return ptri.findByprojectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPtbyProjectsequence(String projectSequence, String backlog_id, String principalUsrname) {
        Project p = ps.findByProjectIdentifier(backlog_id, principalUsrname);
        if (p == null) {
            throw new ProjectNotFoundException("With this id '" + backlog_id + "' project Not Found");
        }

        Backlog backlog = blr.findByprojectIdentifier(backlog_id);
        if (backlog == null) {
            throw new ProjectNotFoundException("With this id '" + projectSequence + "' backlog Not Found");
        }
        ProjectTask projectTaskwithsequence = ptri.findByProjectSequence(projectSequence);
        if (projectTaskwithsequence == null) {
            throw new ProjectNotFoundException("With this id '" + projectSequence + "' projectTask Not Found");
        }
        if (!projectTaskwithsequence.getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectNotFoundException("Project is Not belon To" + backlog_id + " backlog id");
        }
        ProjectTask projectTask = ptri.findByProjectSequenceAndBacklog_projectIdentifier(projectSequence, backlog_id);
        if (projectTask == null) {
            throw new ProjectNotFoundException("With this id '" + projectSequence + "' projectTask Not Found");
        }

        return projectTask;
    }

    public ProjectTask updateProjectTask(ProjectTask task, String backloId, String ptseq, String usenamePrincipal) {
        // Project p = psr.findByProjectIdentifier(backloId);
        Project p = ps.findByProjectIdentifier(backloId, usenamePrincipal);
        if (p == null) {
            throw new ProjectNotFoundException("With this id '" + backloId + "' project Not Found");
        }
        ProjectTask pt = ptri.findByProjectSequence(ptseq);
        if (pt == null) {
            throw new ProjectNotFoundException("With this id '" + ptseq + "' projectTask Not Found");
        }
        if (!pt.getProjectIdentifier().equals(backloId)) {
            throw new ProjectNotFoundException("Project is Not belon To" + backloId + " backlog id");
        }
        ProjectTask projectTask = ptri.findByProjectSequenceAndBacklog_projectIdentifier(ptseq, backloId);
        if (projectTask == null) {
            throw new ProjectNotFoundException("With this id '" + ptseq + "' projectTask Not Found");
        }

        pt = task;

        return ptri.save(pt);
    }

    public String deleteprojecttask(String backloId, String ptseq, String usenamePrincipal) {
        String dataReturn = "";
        // Project p = psr.findByProjectIdentifier(backloId);
        Project p = ps.findByProjectIdentifier(backloId, usenamePrincipal);
        if (p == null) {
            throw new ProjectNotFoundException("With this id '" + backloId + "' project Not Found");
        }
        ProjectTask pt = ptri.findByProjectSequence(ptseq);
        if (pt == null) {
            throw new ProjectNotFoundException("With this id '" + ptseq + "' projectTask Not Found");
        }
        if (!pt.getProjectIdentifier().equals(backloId)) {
            throw new ProjectNotFoundException("Project is Not belon To" + backloId + " backlog id");
        }
        ProjectTask projectTask = ptri.findByProjectSequenceAndBacklog_projectIdentifier(ptseq, backloId);
        if (projectTask == null) {
            throw new ProjectNotFoundException("With this id '" + ptseq + "' projectTask Not Found");
        }

        ptri.delete(projectTask);
        dataReturn = "project task Delete whose ptseq is" + ptseq;
        return dataReturn;
    }
}
