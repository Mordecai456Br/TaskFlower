package com.begodex.taskflower.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.begodex.taskflower.models.team.TeamMembership;
import java.util.Optional;
import java.util.List;

public interface TeamMembershipRepository extends JpaRepository<TeamMembership, Long> {
    Optional<TeamMembership> findByTeamIdAndUserId(Long teamId, Long userId);
    List<TeamMembership> findByTeamId(Long teamId);
    List<TeamMembership> findByUserId(Long userId);
}
