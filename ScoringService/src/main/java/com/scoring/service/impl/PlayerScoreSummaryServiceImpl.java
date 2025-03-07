package com.scoring.service.impl;

import com.scoring.dto.MatchState;
import com.scoring.entities.PlayerScoreSummary;
import com.scoring.enums.EventType;
import com.scoring.repositories.PlayerScoreSummaryRepository;
import com.scoring.service.PlayerScoreSummaryService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerScoreSummaryServiceImpl implements PlayerScoreSummaryService
{
    @Autowired
    private PlayerScoreSummaryRepository playerScoreSummaryRepository;

    private void updateBatsmanScore(PlayerScoreSummary batsmanSummary, ConsumerRecord<UUID, MatchState> record)
    {
        if (!record.value().getEvent_type().equals(EventType.WIDE))
        {
            batsmanSummary.setBallsFaced(batsmanSummary.getBallsFaced() + 1);
        }

        batsmanSummary.setRunsScored(batsmanSummary.getRunsScored() + record.value().getRuns_scored());

        if (record.value().getEvent_type().equals(EventType.FOUR))
        {
            batsmanSummary.setFours(batsmanSummary.getFours() + 1);
        }
        else if (record.value().getEvent_type().equals(EventType.SIX))
        {
            batsmanSummary.setSixes(batsmanSummary.getSixes() + 1);
        }
    }

    private void updateBowlerSummary(PlayerScoreSummary bowlerSummary, ConsumerRecord<UUID, MatchState> record)
    {
        if (!record.value().getEvent_type().equals(EventType.NO_BALL) || !record.value().getEvent_type().equals(EventType.WIDE))
        {
            String oversBowled = String.valueOf(record.value().getOver()) + "." + String.valueOf(record.value().getBall());

            bowlerSummary.setOversBowled(oversBowled);

            if (record.value().isWicket())
            {
                bowlerSummary.setWicketsTaken(bowlerSummary.getWicketsTaken() + 1);
            }
        }
        else if (!record.value().getEvent_type().equals(EventType.NO_BALL) || !record.value().getEvent_type().equals(EventType.WIDE))
        {
            bowlerSummary.setRunsConceded(bowlerSummary.getRunsConceded() + 1);
        }
        bowlerSummary.setRunsConceded(bowlerSummary.getRunsConceded() + record.value().getRuns_scored());
    }

    private PlayerScoreSummary newBatsman(ConsumerRecord<UUID, MatchState> record)
    {
        PlayerScoreSummary newBatsman = new PlayerScoreSummary();

        newBatsman.setMatchId(record.value().getMatch_id());
        newBatsman.setPlayerId(record.value().getStrikerBatsman());
        newBatsman.setRunsScored(0);
        newBatsman.setBallsFaced(0);
        newBatsman.setFours(0);
        newBatsman.setSixes(0);
        newBatsman.setOversBowled("0");
        newBatsman.setWicketsTaken(0);
        newBatsman.setRunsConceded(0);

        if (!record.value().getEvent_type().equals(EventType.WIDE))
        {
            newBatsman.setBallsFaced(newBatsman.getBallsFaced() + 1);
        }

        if (record.value().getEvent_type().equals(EventType.FOUR))
        {
            newBatsman.setFours(newBatsman.getFours() + 1);
        }

        if (record.value().getEvent_type().equals(EventType.SIX))
        {
            newBatsman.setSixes(newBatsman.getSixes() + 1);
        }

        newBatsman.setRunsScored(newBatsman.getRunsScored() + record.value().getRuns_scored());

        return newBatsman;
    }

    private PlayerScoreSummary newBowler(ConsumerRecord<UUID, MatchState> record)
    {
        PlayerScoreSummary newBowler = new PlayerScoreSummary();

        newBowler.setMatchId(record.value().getMatch_id());
        newBowler.setPlayerId(record.value().getBowler());
        newBowler.setRunsScored(0);
        newBowler.setBallsFaced(0);
        newBowler.setFours(0);
        newBowler.setSixes(0);
        newBowler.setOversBowled("0");
        newBowler.setWicketsTaken(0);
        newBowler.setRunsConceded(0);

        if (!record.value().getEvent_type().equals(EventType.NO_BALL) || !record.value().getEvent_type().equals(EventType.WIDE))
        {
            String oversBowled = String.valueOf(record.value().getOver()) + "." + String.valueOf(record.value().getBall());

            newBowler.setOversBowled(oversBowled);

            if (record.value().isWicket())
            {
                newBowler.setWicketsTaken(1);
            }
        }
        else if (!record.value().getEvent_type().equals(EventType.NO_BALL) || !record.value().getEvent_type().equals(EventType.WIDE))
        {
            newBowler.setRunsConceded(newBowler.getRunsConceded() + 1);
        }

        newBowler.setRunsConceded(newBowler.getRunsConceded() + record.value().getRuns_scored());

        return newBowler;
    }

    @Override
    public void updatePlayerScore(ConsumerRecord<UUID, MatchState> record)
    {
        Optional<PlayerScoreSummary> batsmanSummary = playerScoreSummaryRepository.findById(record.value().getStrikerBatsman());

        Optional<PlayerScoreSummary> bowlerSummary = playerScoreSummaryRepository.findById(record.value().getBowler());

        if (batsmanSummary.isPresent())
        {
            updateBatsmanScore(batsmanSummary.get(), record);
            playerScoreSummaryRepository.save(batsmanSummary.get());
        }
        else
        {
            playerScoreSummaryRepository.save(newBatsman(record));
        }

        if (bowlerSummary.isPresent())
        {
            updateBowlerSummary(bowlerSummary.get(), record);
            playerScoreSummaryRepository.save(bowlerSummary.get());
        }
        else
        {
            playerScoreSummaryRepository.save(newBowler(record));
        }
    }

    @Override
    public PlayerScoreSummary getPlayerPerformance(UUID matchId, UUID playerId)
    {
        return playerScoreSummaryRepository.findByMatchIdAndPlayerId(matchId, playerId).get();
    }
}
