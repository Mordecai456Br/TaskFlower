package com.begodex.taskflower.models.project;

import com.begodex.taskflower.models.team.Team;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/* Entidade Project: representa um projeto no sistema */
@Entity
@Table(name = "projects")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String description;

    /* Categoria para projetos pessoais (enum). Para projetos empresariais,
       o campo corporateCategory pode ser utilizado para texto livre. */
    @Enumerated(EnumType.STRING)
    private Category category;

    /* Campo usado quando é um projeto empresarial e a categoria é definida pelo cliente */
    private String corporateCategory;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @Min(0) @Max(100)
    private Integer prdScopePercent; // 0-100

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<Team> teams = new ArrayList<>();

    private Instant createdAt;
    private Instant updatedAt;

}
