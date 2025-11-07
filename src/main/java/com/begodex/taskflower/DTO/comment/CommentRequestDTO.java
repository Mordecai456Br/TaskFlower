package com.begodex.taskflower.DTO.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {
    private Long authorId;
    private String text;
}
