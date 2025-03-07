package com.scoring.service;

import com.scoring.client.PlayerClient;
import com.scoring.dto.BallByBallSummary;
import com.scoring.dto.MatchState;
import com.scoring.dto.ScoreUpdate;
import com.scoring.entities.Scorecard;
import com.scoring.enums.EventType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ConsumerService
{
    @Autowired
    private ScorecardService scorecardService;

    @Autowired
    private PlayerScoreSummaryService playerScoreSummaryService;

    @Autowired
    private PlayerClient playerClient;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ProducerService producerService;

    @KafkaListener(topics = "ball.event", groupId = "ball-event-group", containerFactory = "concurrentKafkaListenerContainerFactory")
    public void receiverBallEvent(ConsumerRecord<UUID, MatchState> record)
    {
        Scorecard scorecard = scorecardService.updateScorecard(record);
        playerScoreSummaryService.updatePlayerScore(record);
        updateBallByBallSummary(record);

        ScoreUpdate scoreUpdate = updateBallByBallSummary(record);

        scoreUpdate.setTotalRuns(scorecard.getTotalRuns());
        scoreUpdate.setWickets(scorecard.getTotalWickets());

        producerService.publishMatchEvent(scoreUpdate);
    }

    private ScoreUpdate updateBallByBallSummary(ConsumerRecord<UUID, MatchState> record)
    {
        BallByBallSummary ballByBallSummary = new BallByBallSummary();

        ballByBallSummary.setOverNumber(record.value().getOver());
        ballByBallSummary.setBallNumber(record.value().getBall());
        ballByBallSummary.setEventType(record.value().getEvent_type());

        String batsman = playerClient.getPlayer(record.value().getStrikerBatsman()).getName();
        String bowler = playerClient.getPlayer(record.value().getBowler()).getName();

        ballByBallSummary.setBatsman(batsman);
        ballByBallSummary.setBowler(bowler);

        redisService.saveBallSummary(record.value().getMatch_id(), ballByBallSummary);

        return updateKafkaProducer(record, batsman, bowler);
    }

    private ScoreUpdate updateKafkaProducer(ConsumerRecord<UUID, MatchState> record, String batsman, String bowler)
    {
        ScoreUpdate scoreUpdate = new ScoreUpdate();

        scoreUpdate.setOver(record.value().getOver());
        scoreUpdate.setBall(record.value().getBall());
        scoreUpdate.setMatchId(record.value().getMatch_id());
        scoreUpdate.setBatsman(batsman);
        scoreUpdate.setBowler(bowler);

        if (!record.value().getEvent_type().equals(EventType.NO_BALL) || !record.value().getEvent_type().equals(EventType.WIDE)
         || !record.value().getEvent_type().equals(EventType.LEG_BYES))
        {
            scoreUpdate.setRuns(Integer.parseInt(record.value().getEvent_type().getValue()));
        }
        return  scoreUpdate;
    }
}
