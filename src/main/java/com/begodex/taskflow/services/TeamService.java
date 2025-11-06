package com.begodex.taskflow.services;

import com.begodex.taskflow.DTO.team.*;
import com.begodex.taskflow.models.team.*;
import com.begodex.taskflow.models.project.Project;
import com.begodex.taskflow.models.user.User;
import com.begodex.taskflow.repositories.*;
import com.begodex.taskflow.exceptions.httpExceptions.EntityNotFoundException;
import com.begodex.taskflow.exceptions.httpExceptions.BadRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMembershipRepository membershipRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    // --- CRUD Team ---

    @Transactional
    public TeamResponseDTO create(Long projectId, TeamRequestDTO req) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project", projectId));
        Team team = Team.builder().name(req.getName()).description(req.getDescription()).project(project).build();
        team = teamRepository.save(team);
        return toTeamDto(team);
    }

    public TeamResponseDTO findById(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Team", id));
        return toTeamDto(team);
    }

    public List<TeamResponseDTO> findByProject(Long projectId) {
        return teamRepository.findByProjectId(projectId).stream().map(this::toTeamDto).collect(Collectors.toList());
    }

    @Transactional
    public TeamResponseDTO update(Long id, TeamRequestDTO req) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Team", id));
        if (req.getName() != null) team.setName(req.getName());
        if (req.getDescription() != null) team.setDescription(req.getDescription());
        return toTeamDto(teamRepository.save(team));
    }

    @Transactional
    public void delete(Long id) {
        if (!teamRepository.existsById(id)) throw new EntityNotFoundException("Team", id);
        teamRepository.deleteById(id);
    }

    // --- Members (TeamMembership) ---

    @Transactional
    public TeamMembershipDTO addMember(Long teamId, TeamMembershipDTO dto) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("Team", teamId));
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new EntityNotFoundException("User", dto.getUserId()));

        if (membershipRepository.findByTeamIdAndUserId(teamId, user.getId()).isPresent()) {
            throw new BadRequestException("User already member of team");
        }

        TeamMembership membership = TeamMembership.builder()
                .team(team)
                .user(user)
                .canCreate(dto.isCanCreate())
                .canEdit(dto.isCanEdit())
                .canDelete(dto.isCanDelete())
                .canRead(dto.isCanRead())
                .canAssign(dto.isCanAssign())
                .role(dto.getRole() != null ? RoleInTeam.valueOf(dto.getRole()) : null)
                .joinedAt(dto.getJoinedAt() != null ? dto.getJoinedAt() : Instant.now())
                .build();

        membership = membershipRepository.save(membership);
        return toMembershipDto(membership);
    }

    @Transactional
    public TeamMembershipDTO updateMemberPermissions(Long teamId, Long userId, TeamMembershipDTO dto) {
        TeamMembership membership = membershipRepository.findByTeamIdAndUserId(teamId, userId)
                .orElseThrow(() -> new EntityNotFoundException("TeamMembership", String.format("team=%d,user=%d", teamId, userId)));

        // aplica apenas campos não-nulos (booleanos vêm como false por padrão, então aplicamos sempre)
        membership.setCanCreate(dto.isCanCreate());
        membership.setCanEdit(dto.isCanEdit());
        membership.setCanDelete(dto.isCanDelete());
        membership.setCanRead(dto.isCanRead());
        membership.setCanAssign(dto.isCanAssign());
        if (dto.getRole() != null) membership.setRole(RoleInTeam.valueOf(dto.getRole()));

        membership = membershipRepository.save(membership);
        return toMembershipDto(membership);
    }

    @Transactional
    public void removeMember(Long teamId, Long userId) {
        TeamMembership membership = membershipRepository.findByTeamIdAndUserId(teamId, userId)
                .orElseThrow(() -> new EntityNotFoundException("TeamMembership", String.format("team=%d,user=%d", teamId, userId)));
        membershipRepository.delete(membership);
    }

    public List<TeamMembershipDTO> listMembers(Long teamId) {
        return membershipRepository.findByTeamId(teamId).stream().map(this::toMembershipDto).collect(Collectors.toList());
    }

    // --- simple mappers (inline and minimal) ---

    private TeamResponseDTO toTeamDto(Team team) {
        List<TeamMembershipDTO> members = membershipRepository.findByTeamId(team.getId())
                .stream().map(this::toMembershipDto).collect(Collectors.toList());
        return TeamResponseDTO.builder()
                .id(team.getId())
                .name(team.getName())
                .description(team.getDescription())
                .projectId(team.getProject().getId())
                .memberships(members)
                .build();
    }

    private TeamMembershipDTO toMembershipDto(TeamMembership membership) {
        return TeamMembershipDTO.builder()
                .id(membership.getId())
                .teamId(membership.getTeam().getId())
                .userId(membership.getUser().getId())
                .canCreate(membership.isCanCreate())
                .canEdit(membership.isCanEdit())
                .canDelete(membership.isCanDelete())
                .canRead(membership.isCanRead())
                .canAssign(membership.isCanAssign())
                .role(membership.getRole() != null ? membership.getRole().name() : null)
                .joinedAt(membership.getJoinedAt())
                .build();
    }
}
