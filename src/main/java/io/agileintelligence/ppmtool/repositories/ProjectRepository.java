/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.agileintelligence.ppmtool.repositories;

import io.agileintelligence.ppmtool.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author SOUMYA SAHOO
 */
@org.springframework.stereotype.Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
