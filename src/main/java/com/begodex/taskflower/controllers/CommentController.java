package com.begodex.taskflower.controllers;

import com.begodex.taskflower.DTO.comment.CommentRequestDTO;
import com.begodex.taskflower.DTO.comment.CommentResponseDTO;
import com.begodex.taskflower.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/tasks/{taskId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDTO> create(@PathVariable Long projectId,
                                                     @PathVariable Long taskId,
                                                     @RequestBody CommentRequestDTO dto) {
        return ResponseEntity.status(201).body(commentService.create(projectId, taskId, dto));
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDTO>> list(@PathVariable Long projectId,
                                                         @PathVariable Long taskId) {
        return ResponseEntity.ok(commentService.list(projectId, taskId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Long projectId,
                                       @PathVariable Long taskId,
                                       @PathVariable Long commentId) {
        commentService.delete(projectId, taskId, commentId);
        return ResponseEntity.noContent().build();
    }
}
