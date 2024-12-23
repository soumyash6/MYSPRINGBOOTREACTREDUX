package io.agileintelligence.ppmtool.repositories;

import io.agileintelligence.ppmtool.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByProjectIdentifier(String projectId);

    @Override
    List<Project> findAll();

    List<Project> findAllByProjectLeader(String projectLeader);

    /*
     * @Query(value = "select * from projects where id=:id", nativeQuery = true)
     * List<Project> getAllProjects(@Param(value = "id") String id);
     */

}