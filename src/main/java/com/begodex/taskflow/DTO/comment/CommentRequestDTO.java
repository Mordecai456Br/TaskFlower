package com.begodex.taskflow.DTO.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {
    private Long authorId;
    private String text;
}
