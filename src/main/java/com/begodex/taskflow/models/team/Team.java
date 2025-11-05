package com.begodex.taskflow.models.team;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import com.begodex.taskflow.models.project.Project;
import com.begodex.taskflow.models.user.User;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "teams")
@Getter
@Setter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;


    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private java.util.List<TeamMembership> memberships = new java.util.ArrayList<>();
}
