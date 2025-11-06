package com.begodex.taskflow.DTO.task;

import com.begodex.taskflow.models.task.TaskStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskStatusUpdateDTO {
    private TaskStatus status;
}