package com.begodex.taskflower.DTO.team;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TeamRequestDTO {
    private String name;
    private String description;
    private Long projectId;
}
