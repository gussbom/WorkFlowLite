package com.gusbomcode.workflowlite.project.repositories;

import com.gusbomcode.workflowlite.project.entities.Project;
import com.gusbomcode.workflowlite.project.enums.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    boolean existsByName(String name);

    Page<Project> findAllByStatus(ProjectStatus status, Pageable page);
}
