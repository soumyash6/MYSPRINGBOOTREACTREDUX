/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.Exception.ProjectNotFoundException;
import io.agileintelligence.ppmtool.domain.Backlog;
import io.agileintelligence.ppmtool.domain.ProjectTask;
import io.agileintelligence.ppmtool.repositories.Backlogrepositoryinterface;
import io.agileintelligence.ppmtool.repositories.ProjectTaskRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author SOUMYA SAHOO
 */
@Service
public class ProjectTaskService {

    @Autowired
    private Backlogrepositoryinterface blr;

    @Autowired
    private ProjectTaskRepositoryInterface ptri;

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
        return ptri.findByprojectIdentifierOrderByPriority(id);
    }
}
