package com.scoring.service;

import com.scoring.dto.MatchState;
import com.scoring.entities.PlayerScoreSummary;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.UUID;

public interface PlayerScoreSummaryService
{
    void updatePlayerScore(ConsumerRecord<UUID, MatchState> record);

    PlayerScoreSummary getPlayerPerformance(UUID matchId, UUID playerId);
}
