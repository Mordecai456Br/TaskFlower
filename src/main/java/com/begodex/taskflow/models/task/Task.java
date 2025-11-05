package com.begodex.taskflow.models.task;

import jakarta.persistence.*;
import java.time.Instant;
import com.begodex.taskflow.models.project.Project;
import com.begodex.taskflow.models.team.Team;
import com.begodex.taskflow.models.user.User;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 4000)
    private String description;

    /* Relacionamento com o projeto: obrigatório (task pertence a um projeto) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    /* Relacionamento opcional com equipe */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = true)
    private Team team;

    /* Responsável pela task (pode ser null se ainda não atribuída) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsible_id", nullable = true)
    private User responsible;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private Instant dueDate;

    /* Estimativa em pomodoros (inteiro) -- pode servir de peso para cálculo de progresso */
    private Integer estimatedPomodoros;

    /* Peso (float) alternativo para cálculo do percentual do projeto */
    private Float weight;

    private Instant createdAt;
    private Instant doneAt;

}
