package com.begodex.taskflower.services;

import com.begodex.taskflower.DTO.comment.CommentRequestDTO;
import com.begodex.taskflower.DTO.comment.CommentResponseDTO;
import com.begodex.taskflower.exceptions.httpExceptions.EntityNotFoundException;
import com.begodex.taskflower.models.Comment;
import com.begodex.taskflower.models.task.Task;
import com.begodex.taskflower.models.user.User;
import com.begodex.taskflower.repositories.CommentRepository;
import com.begodex.taskflower.repositories.TaskRepository;
import com.begodex.taskflower.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponseDTO create(Long projectId, Long taskId, CommentRequestDTO dto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task", taskId));

        User author = userRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("User", dto.getAuthorId()));

        Comment comment = Comment.builder()
                .task(task)
                .author(author)
                .text(dto.getText())
                .createdAt(Instant.now())
                .build();

        return toDto(commentRepository.save(comment));
    }

    public List<CommentResponseDTO> list(Long projectId, Long taskId) {
        return commentRepository.findByTaskId(taskId)
                .stream().map(this::toDto).toList();
    }

    @Transactional
    public void delete(Long projectId, Long taskId, Long commentId) {
        if (!commentRepository.existsById(commentId))
            throw new EntityNotFoundException("Comment", commentId);
        commentRepository.deleteById(commentId);
    }

    private CommentResponseDTO toDto(Comment c) {
        return CommentResponseDTO.builder()
                .id(c.getId())
                .taskId(c.getTask().getId())
                .authorId(c.getAuthor().getId())
                .text(c.getText())
                .createdAt(c.getCreatedAt())
                .build();
    }
}
