package com.begodex.taskflow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.begodex.taskflow.models.task.Task;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(Long projectId);
    List<Task> findByTeamId(Long teamId);
    List<Task> findByResponsibleId(Long userId);
}
