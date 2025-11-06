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
        Project p = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project", projectId));
        Team t = Team.builder().name(req.getName()).description(req.getDescription()).project(p).build();
        t = teamRepository.save(t);
        return toTeamDto(t);
    }

    public TeamResponseDTO findById(Long id) {
        Team t = teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Team", id));
        return toTeamDto(t);
    }

    public List<TeamResponseDTO> findByProject(Long projectId) {
        return teamRepository.findByProjectId(projectId).stream().map(this::toTeamDto).collect(Collectors.toList());
    }

    @Transactional
    public TeamResponseDTO update(Long id, TeamRequestDTO req) {
        Team t = teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Team", id));
        if (req.getName() != null) t.setName(req.getName());
        if (req.getDescription() != null) t.setDescription(req.getDescription());
        return toTeamDto(teamRepository.save(t));
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

        TeamMembership m = TeamMembership.builder()
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

        m = membershipRepository.save(m);
        return toMembershipDto(m);
    }

    @Transactional
    public TeamMembershipDTO updateMemberPermissions(Long teamId, Long userId, TeamMembershipDTO dto) {
        TeamMembership m = membershipRepository.findByTeamIdAndUserId(teamId, userId)
                .orElseThrow(() -> new EntityNotFoundException("TeamMembership", String.format("team=%d,user=%d", teamId, userId)));

        // aplica apenas campos não-nulos (booleanos vêm como false por padrão, então aplicamos sempre)
        m.setCanCreate(dto.isCanCreate());
        m.setCanEdit(dto.isCanEdit());
        m.setCanDelete(dto.isCanDelete());
        m.setCanRead(dto.isCanRead());
        m.setCanAssign(dto.isCanAssign());
        if (dto.getRole() != null) m.setRole(RoleInTeam.valueOf(dto.getRole()));

        m = membershipRepository.save(m);
        return toMembershipDto(m);
    }

    @Transactional
    public void removeMember(Long teamId, Long userId) {
        TeamMembership m = membershipRepository.findByTeamIdAndUserId(teamId, userId)
                .orElseThrow(() -> new EntityNotFoundException("TeamMembership", String.format("team=%d,user=%d", teamId, userId)));
        membershipRepository.delete(m);
    }

    public List<TeamMembershipDTO> listMembers(Long teamId) {
        return membershipRepository.findByTeamId(teamId).stream().map(this::toMembershipDto).collect(Collectors.toList());
    }

    // --- simple mappers (inline and minimal) ---

    private TeamResponseDTO toTeamDto(Team t) {
        List<TeamMembershipDTO> members = membershipRepository.findByTeamId(t.getId())
                .stream().map(this::toMembershipDto).collect(Collectors.toList());
        return TeamResponseDTO.builder()
                .id(t.getId())
                .name(t.getName())
                .description(t.getDescription())
                .projectId(t.getProject().getId())
                .memberships(members)
                .build();
    }

    private TeamMembershipDTO toMembershipDto(TeamMembership m) {
        return TeamMembershipDTO.builder()
                .id(m.getId())
                .teamId(m.getTeam().getId())
                .userId(m.getUser().getId())
                .canCreate(m.isCanCreate())
                .canEdit(m.isCanEdit())
                .canDelete(m.isCanDelete())
                .canRead(m.isCanRead())
                .canAssign(m.isCanAssign())
                .role(m.getRole() != null ? m.getRole().name() : null)
                .joinedAt(m.getJoinedAt())
                .build();
    }
}
