package com.begodex.taskflow.DTO.team;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TeamResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Long projectId;
    private List<TeamMembershipDTO> memberships;
}
