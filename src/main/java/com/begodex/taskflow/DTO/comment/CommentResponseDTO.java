package com.begodex.taskflow.DTO.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter @Setter @Builder
public class CommentResponseDTO {
    private Long id;
    private Long authorId;
    private Long taskId;
    private String text;
    private Instant createdAt;
}
