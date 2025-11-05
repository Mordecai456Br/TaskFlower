package com.begodex.taskflow.DTO.team;

import lombok.*;

import java.time.Instant;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamMembershipDTO {
    private Long id;
    private Long teamId;
    private Long userId;
    private boolean canCreate;
    private boolean canEdit;
    private boolean canDelete;
    private boolean canRead;
    private boolean canAssociate;
    private String role;
    private Instant joinedAt;


}
