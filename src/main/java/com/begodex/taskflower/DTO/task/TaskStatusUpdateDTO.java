package com.begodex.taskflower.DTO.task;

import com.begodex.taskflower.models.task.TaskStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskStatusUpdateDTO {
    private TaskStatus status;
}