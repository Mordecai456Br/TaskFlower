package com.begodex.taskflower.repositories;

import com.begodex.taskflower.models.project.Category;
import com.begodex.taskflower.models.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByCategory(Category category);

    List<Project> findByCorporateCategory(String corporateCategory);
}
