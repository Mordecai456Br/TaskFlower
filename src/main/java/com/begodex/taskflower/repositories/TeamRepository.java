package com.begodex.taskflower.repositories;

import com.begodex.taskflower.models.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByProjectId(Long projectId);
}
