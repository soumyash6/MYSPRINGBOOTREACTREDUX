/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.agileintelligence.ppmtool.repositories;

/**
 * @author SOUMYA SAHOO
 */

import io.agileintelligence.ppmtool.domain.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Backlogrepositoryinterface extends CrudRepository<Backlog, Long> {

    Backlog findByprojectIdentifier(String identifier);

}
