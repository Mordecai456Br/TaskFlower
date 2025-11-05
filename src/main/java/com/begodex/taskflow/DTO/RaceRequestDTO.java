package com.begodex.taskflow.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RaceRequestDTO {
    private String name;
    private LocalDateTime dateRace;
    private Long placeId;
}
