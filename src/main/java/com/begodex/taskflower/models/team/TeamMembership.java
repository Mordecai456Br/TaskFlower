package com.begodex.taskflower.models.team;

import jakarta.persistence.*;
import java.time.Instant;
import com.begodex.taskflower.models.user.User;
import lombok.*;


@Entity
@Table(name = "team_memberships",
        uniqueConstraints = @UniqueConstraint(columnNames = {"team_id", "user_id"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TeamMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* Team dono da associação */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    /* User dono da associação */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    private boolean canCreate;
    private boolean canEdit;
    private boolean canDelete;
    private boolean canRead;
    private boolean canAssign;

    @Enumerated(EnumType.STRING)
    private com.begodex.taskflower.models.team.RoleInTeam role;

    private Instant joinedAt;



}
