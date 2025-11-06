package com.begodex.taskflow.controllers;

import com.begodex.taskflow.DTO.team.*;
import com.begodex.taskflow.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/projects/{projectId}/teams")
    public ResponseEntity<TeamResponseDTO> create(@PathVariable Long projectId, @RequestBody TeamRequestDTO request) {
        TeamResponseDTO created = teamService.create(projectId, request);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/projects/{projectId}/teams")
    public ResponseEntity<List<TeamResponseDTO>> listByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(teamService.findByProject(projectId));
    }

    @GetMapping("/teams/{teamId}")
    public ResponseEntity<TeamResponseDTO> get(@PathVariable Long teamId) {
        return ResponseEntity.ok(teamService.findById(teamId));
    }

    @PutMapping("/teams/{teamId}")
    public ResponseEntity<TeamResponseDTO> update(@PathVariable Long teamId, @RequestBody TeamRequestDTO request) {
        return ResponseEntity.ok(teamService.update(teamId, request));
    }

    @DeleteMapping("/teams/{teamId}")
    public ResponseEntity<Void> delete(@PathVariable Long teamId) {
        teamService.delete(teamId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/teams/{teamId}/members")
    public ResponseEntity<TeamMembershipDTO> addMember(@PathVariable Long teamId, @RequestBody TeamMembershipDTO dto) {
        TeamMembershipDTO created = teamService.addMember(teamId, dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/teams/{teamId}/members/{userId}")
    public ResponseEntity<TeamMembershipDTO> updateMember(@PathVariable Long teamId, @PathVariable Long userId,
                                                          @RequestBody TeamMembershipDTO dto) {
        return ResponseEntity.ok(teamService.updateMemberPermissions(teamId, userId, dto));
    }

    @DeleteMapping("/teams/{teamId}/members/{userId}")
    public ResponseEntity<Void> removeMember(@PathVariable Long teamId, @PathVariable Long userId) {
        teamService.removeMember(teamId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/teams/{teamId}/members")
    public ResponseEntity<List<TeamMembershipDTO>> listMembers(@PathVariable Long teamId) {
        return ResponseEntity.ok(teamService.listMembers(teamId));
    }
}
