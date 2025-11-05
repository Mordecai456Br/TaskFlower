package com.begodex.taskflow.models.team;

import jakarta.persistence.*;
import java.time.Instant;
import com.begodex.taskflow.models.user.User;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "team_memberships",
        uniqueConstraints = @UniqueConstraint(columnNames = {"team_id", "user_id"}))
@Getter
@Setter
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
    private boolean canAssociate;

    @Enumerated(EnumType.STRING)
    private com.begodex.taskflow.models.team.RoleInTeam role;

    private Instant joinedAt;


}
