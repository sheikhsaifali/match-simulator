package com.scoring.service;

import com.scoring.dto.DetailedScorecardResponse;
import com.scoring.dto.MatchState;
import com.scoring.dto.MatchSummaryResponse;
import com.scoring.entities.Scorecard;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.UUID;

public interface ScorecardService
{
    Scorecard updateScorecard(ConsumerRecord<UUID, MatchState> record);

    DetailedScorecardResponse displayDetailedScorecard(UUID matchId);

    MatchSummaryResponse matchSummary(UUID matchId);
}
