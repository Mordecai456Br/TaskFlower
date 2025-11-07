package com.begodex.taskflower.models.team;

import com.begodex.taskflower.models.project.Project;
import com.begodex.taskflower.models.task.Task;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

/* Entidade Team */
@Entity
@Table(name = "teams")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Team {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TeamMembership> memberships = new ArrayList<>();

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();
}
