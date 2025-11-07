package com.begodex.taskflower.services;

import com.begodex.taskflower.DTO.ProjectRequestDTO;
import com.begodex.taskflower.exceptions.httpExceptions.EntityNotFoundException;
import com.begodex.taskflower.models.project.Category;
import com.begodex.taskflower.models.project.Project;
import com.begodex.taskflower.models.project.ProjectStatus;
import com.begodex.taskflower.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    /* Retorna todas os projetos */
    public List<Project> getAllProjects() {
        return new ArrayList<>(projectRepository.findAll());
    }

    /* Retorna um projeto por id, lança EntityNotFoundException caso não exista */
    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project", projectId));
    }

    @Transactional
    public Project createProject(ProjectRequestDTO request) {
        Project project = new Project();
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setCategory(request.getCategory());
        project.setCorporateCategory(request.getCorporateCategory());
        project.setStatus(request.getStatus() != null ? request.getStatus() : ProjectStatus.DRAFT);
        project.setPrdScopePercent(request.getPrdScopePercent() != null ? request.getPrdScopePercent() : 0);
        project.setCreatedAt(Instant.now());
        project.setUpdatedAt(Instant.now());
        return projectRepository.save(project);
    }

    @Transactional
    public Project updateProject(Long projectId, ProjectRequestDTO request) {
        Project existing = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project", projectId));

        // Atualiza somente campos permitidos
        if (request.getTitle() != null) existing.setTitle(request.getTitle());
        if (request.getDescription() != null) existing.setDescription(request.getDescription());
        if (request.getCategory() != null) existing.setCategory(request.getCategory());
        if (request.getCorporateCategory() != null) existing.setCorporateCategory(request.getCorporateCategory());
        if (request.getStatus() != null) existing.setStatus(request.getStatus());
        if (request.getPrdScopePercent() != null) existing.setPrdScopePercent(request.getPrdScopePercent());

        existing.setUpdatedAt(Instant.now());
        return projectRepository.save(existing);
    }

    @Transactional
    public void deleteProject(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new EntityNotFoundException("Project", projectId);
        }
        projectRepository.deleteById(projectId);
    }

    /* Busca por categoria pessoal */
    public List<Project> findByCategory(Category category) {
        return projectRepository.findByCategory(category);
    }

    /* Busca por corporate category texto */
    public List<Project> findByCorporateCategory(String corporateCategory) {
        return projectRepository.findByCorporateCategory(corporateCategory);
    }
}
