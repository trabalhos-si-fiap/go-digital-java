package com.laroz.repositories;

import com.laroz.models.Project;
import com.laroz.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Page<Project> findByIsActiveTrue(Pageable page);
}
