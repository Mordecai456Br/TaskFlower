
package com.begodex.taskflower.DTO.task;

import java.time.Instant;
import com.begodex.taskflower.models.task.TaskStatus;
import com.begodex.taskflower.models.task.Priority;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskRequestDTO {
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

}
