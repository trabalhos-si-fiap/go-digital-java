package com.laroz.repositories;

import com.laroz.models.Project;
import com.laroz.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
