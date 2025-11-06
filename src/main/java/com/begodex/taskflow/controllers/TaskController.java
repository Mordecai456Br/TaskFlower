package com.begodex.taskflow.controllers;

import com.begodex.taskflow.DTO.task.*;
import com.begodex.taskflow.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponseDTO>> listAll() {
        return ResponseEntity.ok(taskService.findAll());
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<List<TaskResponseDTO>> listByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(taskService.findByProject(projectId));
    }

    @GetMapping("/teams/{teamId}/tasks")
    public ResponseEntity<List<TaskResponseDTO>> listByTeam(@PathVariable Long teamId) {
        return ResponseEntity.ok(taskService.findByTeam(teamId));
    }

    @GetMapping("/users/{userId}/tasks")
    public ResponseEntity<List<TaskResponseDTO>> listByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.findByResponsible(userId));
    }

    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<TaskResponseDTO> create(@PathVariable Long projectId, @RequestBody TaskRequestDTO req) {
        // ensure request.projectId set from path if not provided
        if (req.getProjectId() == null) req.setProjectId(projectId);
        TaskResponseDTO created = taskService.create(req);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskResponseDTO> update(@PathVariable Long id, @RequestBody TaskRequestDTO req) {
        return ResponseEntity.ok(taskService.update(id, req));
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/tasks/{taskId}/assignees/{userId}")
    public ResponseEntity<TaskResponseDTO> assign(@PathVariable Long taskId, @PathVariable Long userId) {
        return ResponseEntity.ok(taskService.assignResponsible(taskId, userId));
    }

    @DeleteMapping("/tasks/{taskId}/assignees")
    public ResponseEntity<TaskResponseDTO> unassign(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.unassignResponsible(taskId));
    }

    @PatchMapping("/tasks/{taskId}/status")
    public ResponseEntity<TaskResponseDTO> changeStatus(@PathVariable Long taskId, @RequestBody TaskStatusUpdateDTO dto) {
        return ResponseEntity.ok(taskService.changeStatus(taskId, dto.getStatus()));
    }
}
