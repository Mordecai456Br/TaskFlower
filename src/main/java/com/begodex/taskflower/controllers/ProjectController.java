package com.begodex.taskflower.controllers;

import java.util.List;

import com.begodex.taskflower.DTO.ProjectRequestDTO;
import com.begodex.taskflower.DTO.team.TeamRequestDTO;
import com.begodex.taskflower.DTO.team.TeamResponseDTO;
import com.begodex.taskflower.models.project.Category;
import com.begodex.taskflower.models.project.Project;
import com.begodex.taskflower.services.ProjectService;

import com.begodex.taskflower.services.TeamService;
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

    @Autowired
    private TeamService teamService;

    @PostMapping("/{projectId}/teams")
    public ResponseEntity<TeamResponseDTO> create(@PathVariable Long projectId, @RequestBody TeamRequestDTO request) {
        TeamResponseDTO created = teamService.create(projectId, request);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/{projectId}/teams")
    public ResponseEntity<List<TeamResponseDTO>> listByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(teamService.findByProject(projectId));
    }

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
