package com.scoring.repositories;

import com.scoring.entities.PlayerScoreSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayerScoreSummaryRepository extends JpaRepository<PlayerScoreSummary, UUID>
{
    Optional<PlayerScoreSummary> findByMatchIdAndPlayerId(UUID matchId, UUID playedId);

    PlayerScoreSummary findByMatchIdOrderByRunsScoredDescBallsFacedAsc(UUID matchId);

    PlayerScoreSummary findByMatchIdOrderByWicketsTakenDescRunsConcededAsc(UUID matchId);
}
