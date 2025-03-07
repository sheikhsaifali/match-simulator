package com.scoring.repositories;

import com.scoring.entities.Scorecard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ScorecardRepository extends JpaRepository<Scorecard, UUID>
{
    Optional<Scorecard> findByMatchIdAndTeamId(UUID matchId, UUID teamId);
}
