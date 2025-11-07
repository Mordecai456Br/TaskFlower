package com.begodex.taskflower.services;

import com.begodex.taskflower.DTO.task.*;
import com.begodex.taskflower.models.task.*;
import com.begodex.taskflower.models.project.Project;
import com.begodex.taskflower.models.team.Team;
import com.begodex.taskflower.models.team.TeamMembership;
import com.begodex.taskflower.models.user.User;
import com.begodex.taskflower.repositories.*;
import com.begodex.taskflower.exceptions.httpExceptions.EntityNotFoundException;
import com.begodex.taskflower.exceptions.httpExceptions.BadRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMembershipRepository membershipRepository;

    // create
    @Transactional
    public TaskResponseDTO create(TaskRequestDTO req) {
        Project project = projectRepository.findById(req.getProjectId())
                .orElseThrow(() -> new EntityNotFoundException("Project", req.getProjectId()));

        Team team = null;
        if (req.getTeamId() != null) {
            team = teamRepository.findById(req.getTeamId())
                    .orElseThrow(() -> new EntityNotFoundException("Team", req.getTeamId()));
            if (!team.getProject().getId().equals(project.getId())) {
                throw new BadRequestException("Team does not belong to project");
            }
        }

        User responsible = null;
        if (req.getResponsibleId() != null) {
            responsible = userRepository.findById(req.getResponsibleId())
                    .orElseThrow(() -> new EntityNotFoundException("User", req.getResponsibleId()));
            // se task ligada a team, validar permissão canAssign
            if (team != null) {
                TeamMembership tm = membershipRepository.findByTeamIdAndUserId(team.getId(), responsible.getId())
                        .orElseThrow(() -> new BadRequestException("User not member of team"));
                if (!tm.isCanAssign()) throw new BadRequestException("User cannot be assigned to tasks in this team");
            }
        }

        Task t = Task.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .project(project)
                .team(team)
                .responsible(responsible)
                .status(req.getStatus() != null ? req.getStatus() : TaskStatus.TODO)
                .priority(req.getPriority())
                .dueDate(req.getDueDate())
                .estimatedPomodoros(req.getEstimatedPomodoros())
                .weight(req.getWeight())
                .createdAt(Instant.now())
                .build();

        Task saved = taskRepository.save(t);
        return toDto(saved);
    }

    // read
    public TaskResponseDTO findById(Long id) {
        Task t = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task", id));
        return toDto(t);
    }

    public List<TaskResponseDTO> findAll() {
        return taskRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<TaskResponseDTO> findByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId).stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<TaskResponseDTO> findByTeam(Long teamId) {
        return taskRepository.findByTeamId(teamId).stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<TaskResponseDTO> findByResponsible(Long userId) {
        return taskRepository.findByResponsibleId(userId).stream().map(this::toDto).collect(Collectors.toList());
    }

    // update
    @Transactional
    public TaskResponseDTO update(Long id, TaskRequestDTO req) {
        Task t = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task", id));

        if (req.getTitle() != null) t.setTitle(req.getTitle());
        if (req.getDescription() != null) t.setDescription(req.getDescription());

        if (req.getProjectId() != null && !req.getProjectId().equals(t.getProject().getId())) {
            Project p = projectRepository.findById(req.getProjectId())
                    .orElseThrow(() -> new EntityNotFoundException("Project", req.getProjectId()));
            t.setProject(p);
        }

        if (req.getTeamId() != null) {
            Team newTeam = teamRepository.findById(req.getTeamId())
                    .orElseThrow(() -> new EntityNotFoundException("Team", req.getTeamId()));
            if (!newTeam.getProject().getId().equals(t.getProject().getId())) {
                throw new BadRequestException("Team does not belong to task's project");
            }
            t.setTeam(newTeam);
        }

        if (req.getResponsibleId() != null) {
            User u = userRepository.findById(req.getResponsibleId())
                    .orElseThrow(() -> new EntityNotFoundException("User", req.getResponsibleId()));
            if (t.getTeam() != null) {
                TeamMembership tm = membershipRepository.findByTeamIdAndUserId(t.getTeam().getId(), u.getId())
                        .orElseThrow(() -> new BadRequestException("User not member of task's team"));
                if (!tm.isCanAssign()) throw new BadRequestException("User cannot be assigned to tasks in this team");
            }
            t.setResponsible(u);
        }

        if (req.getStatus() != null) {
            t.setStatus(req.getStatus());
            if (req.getStatus() == TaskStatus.DONE) t.setDoneAt(Instant.now());
            else t.setDoneAt(null);
        }

        if (req.getPriority() != null) t.setPriority(req.getPriority());
        if (req.getDueDate() != null) t.setDueDate(req.getDueDate());
        if (req.getEstimatedPomodoros() != null) t.setEstimatedPomodoros(req.getEstimatedPomodoros());
        if (req.getWeight() != null) t.setWeight(req.getWeight());

        Task saved = taskRepository.save(t);
        return toDto(saved);
    }

    // delete
    @Transactional
    public void delete(Long id) {
        if (!taskRepository.existsById(id)) throw new EntityNotFoundException("Task", id);
        taskRepository.deleteById(id);
    }

    // assign responsible
    @Transactional
    public TaskResponseDTO assignResponsible(Long taskId, Long userId) {
        Task t = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task", taskId));
        User u = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User", userId));

        if (t.getTeam() != null) {
            TeamMembership tm = membershipRepository.findByTeamIdAndUserId(t.getTeam().getId(), u.getId())
                    .orElseThrow(() -> new BadRequestException("User not member of task's team"));
            if (!tm.isCanAssign()) throw new BadRequestException("User cannot be assigned to tasks in this team");
        }

        t.setResponsible(u);
        Task saved = taskRepository.save(t);
        return toDto(saved);
    }

    @Transactional
    public TaskResponseDTO unassignResponsible(Long taskId) {
        Task t = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task", taskId));
        t.setResponsible(null);
        Task saved = taskRepository.save(t);
        return toDto(saved);
    }

    @Transactional
    public TaskResponseDTO changeStatus(Long taskId, TaskStatus status) {
        Task t = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task", taskId));
        t.setStatus(status);
        if (status == TaskStatus.DONE) t.setDoneAt(Instant.now()); else t.setDoneAt(null);
        Task saved = taskRepository.save(t);
        return toDto(saved);
    }

    // simple mapper
    private TaskResponseDTO toDto(Task task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .projectId(task.getProject() != null ? task.getProject().getId() : null)
                .teamId(task.getTeam() != null ? task.getTeam().getId() : null)
                .responsibleId(task.getResponsible() != null ? task.getResponsible().getId() : null)
                .status(task.getStatus() != null ? task.getStatus() : null)
                .priority(task.getPriority() != null ? task.getPriority() : null)
                .dueDate(task.getDueDate())
                .estimatedPomodoros(task.getEstimatedPomodoros())
                .weight(task.getWeight())
                .createdAt(task.getCreatedAt())
                .doneAt(task.getDoneAt())
                .build();
    }
}
