package com.begodex.taskflow.repositories;

import com.begodex.taskflow.models.project.Category;
import com.begodex.taskflow.models.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByCategory(Category category);

    List<Project> findByCorporateCategory(String corporateCategory);
}
