package com.begodex.taskflow.DTO.task;

import java.time.Instant;
import com.begodex.taskflow.models.task.TaskStatus;
import com.begodex.taskflow.models.task.Priority;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Long projectId;
    private Long teamId;
    private Long responsibleId;
    private TaskStatus status;
    private Priority priority;
    private Instant dueDate;
    private Integer estimatedPomodoros;
    private Float weight;
    private Instant createdAt;
    private Instant doneAt;

}
