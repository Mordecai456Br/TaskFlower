package com.begodex.taskflow.controllers;

import com.begodex.taskflow.DTO.team.*;
import com.begodex.taskflow.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("teams")
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamResponseDTO> getById(@PathVariable Long teamId) {
        return ResponseEntity.ok(teamService.findById(teamId));
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<TeamResponseDTO> update(@PathVariable Long teamId, @RequestBody TeamRequestDTO request) {
        return ResponseEntity.ok(teamService.update(teamId, request));
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> delete(@PathVariable Long teamId) {
        teamService.delete(teamId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{teamId}/members")
    public ResponseEntity<TeamMembershipDTO> addMember(@PathVariable Long teamId, @RequestBody TeamMembershipDTO dto) {
        TeamMembershipDTO created = teamService.addMember(teamId, dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{teamId}/members/{userId}")
    public ResponseEntity<TeamMembershipDTO> updateMember(@PathVariable Long teamId, @PathVariable Long userId,
                                                          @RequestBody TeamMembershipDTO dto) {
        return ResponseEntity.ok(teamService.updateMemberPermissions(teamId, userId, dto));
    }

    @DeleteMapping("/{teamId}/members/{userId}")
    public ResponseEntity<Void> removeMember(@PathVariable Long teamId, @PathVariable Long userId) {
        teamService.removeMember(teamId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{teamId}/members")
    public ResponseEntity<List<TeamMembershipDTO>> listMembers(@PathVariable Long teamId) {
        return ResponseEntity.ok(teamService.listMembers(teamId));
    }
}
