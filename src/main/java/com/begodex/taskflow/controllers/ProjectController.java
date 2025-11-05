package com.begodex.taskflow.controllers;

import java.util.List;

import com.begodex.taskflow.DTO.ProjectRequestDTO;
import com.begodex.taskflow.models.project.Category;
import com.begodex.taskflow.models.project.Project;
import com.begodex.taskflow.services.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/* Controller REST para Project.
   Comentários em português explicam decisões principais. */
@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    @GetMapping
    public ResponseEntity<List<Project>> getAll() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getById(@PathVariable("id") Long id) {
        Project project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    @PostMapping
    public ResponseEntity<Project> create(@RequestBody ProjectRequestDTO request) {
        Project created = projectService.createProject(request);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> update(@PathVariable("id") Long id,
                                          @RequestBody ProjectRequestDTO request) {
        Project updated = projectService.updateProject(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Project>> findByCategory(@PathVariable("category") Category category) {
        List<Project> projects = projectService.findByCategory(category);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/corporate/{corporateCategory}")
    public ResponseEntity<List<Project>> findByCorporateCategory(@PathVariable("corporateCategory") String corporateCategory) {
        List<Project> projects = projectService.findByCorporateCategory(corporateCategory);
        return ResponseEntity.ok(projects);
    }
}
