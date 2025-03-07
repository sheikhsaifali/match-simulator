package com.scoring.service.impl;

import com.scoring.client.MatchClient;
import com.scoring.client.PlayerClient;
import com.scoring.client.TeamClient;
import com.scoring.dto.*;
import com.scoring.entities.PlayerScoreSummary;
import com.scoring.entities.Scorecard;
import com.scoring.enums.EventType;
import com.scoring.repositories.PlayerScoreSummaryRepository;
import com.scoring.repositories.ScorecardRepository;
import com.scoring.service.ScorecardService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ScorecardServiceImpl implements ScorecardService
{
    @Autowired
    private ScorecardRepository scorecardRepository;

    @Autowired
    private PlayerScoreSummaryRepository playerScoreSummaryRepository;

    @Autowired
    private TeamClient teamClient;

    @Autowired
    private PlayerClient playerClient;

    @Autowired
    private MatchClient matchClient;

    static UUID battingTeamId;
    static UUID bowlingTeamId;

    @Override
    public Scorecard updateScorecard(ConsumerRecord<UUID, MatchState> record)
    {
        Optional<Scorecard> scorecard = scorecardRepository.findById(record.value().getBatting_team());
        battingTeamId = record.value().getBatting_team();
        bowlingTeamId = record.value().getBowling_team();
        Scorecard updatedScorecard;

        if (scorecard.isPresent())
        {
            updateExistingScorecard(scorecard.get(), record);
            updatedScorecard = scorecardRepository.save(scorecard.get());
        }
        else
        {
            updatedScorecard = scorecardRepository.save(newScorecard(record));
        }
        return updatedScorecard;
    }

    @Override
    public DetailedScorecardResponse displayDetailedScorecard(UUID matchId)
    {
        DetailedScorecardResponse detailedScorecardResponse = new DetailedScorecardResponse();

        Optional<Scorecard> scorecard = scorecardRepository.findByMatchIdAndTeamId(matchId, battingTeamId);

        if (scorecard.isPresent())
        {
            detailedScorecardResponse.setTotalRuns(scorecard.get().getTotalRuns());
            detailedScorecardResponse.setTotalWickets(scorecard.get().getTotalWickets());
            detailedScorecardResponse.setMatchId(scorecard.get().getMatchId());
            detailedScorecardResponse.setTotalOvers(scorecard.get().getOversCompleted());
            detailedScorecardResponse.setBattingTeam(teamClient.getTeamByTeamId(battingTeamId).getTeamName());

            CurrentBatsman currentBatsman = fetchBatsmanDetails(matchId, scorecard.get().getCurrentBatsman());

            CurrentBatsman nonStrikerBatsman = fetchBatsmanDetails(matchId, scorecard.get().getNonStrikerBatsman());

            detailedScorecardResponse.setCurrentBatsmen(new CurrentBatsmen(currentBatsman, nonStrikerBatsman));
            detailedScorecardResponse.setCurrentBowler(fetchBowlerDetails(matchId, scorecard.get().getCurrentBowler()));
        }

        return detailedScorecardResponse;
    }

    private CurrentBatsman fetchBatsmanDetails(UUID matchId, UUID playerId)
    {
        CurrentBatsman currentBatsman = new CurrentBatsman();

        Player player = playerClient.getPlayer(playerId);

        if (player != null)
        {
            currentBatsman.setPlayerId(player.getPlayer_id());
            currentBatsman.setName(player.getName());

            Optional<PlayerScoreSummary> playerScoreSummary = playerScoreSummaryRepository.findByMatchIdAndPlayerId(matchId, playerId);

            if (playerScoreSummary.isPresent())
            {
                currentBatsman.setRuns(playerScoreSummary.get().getRunsScored());
                currentBatsman.setBallsFaced(playerScoreSummary.get().getBallsFaced());
                currentBatsman.setFours(playerScoreSummary.get().getFours());
                currentBatsman.setSixes(playerScoreSummary.get().getSixes());
            }
        }
        return currentBatsman;
    }

    private CurrentBowler fetchBowlerDetails(UUID matchId, UUID playerId)
    {
        CurrentBowler currentBowler = new CurrentBowler();

        Player player = playerClient.getPlayer(playerId);

        if (player != null)
        {
            currentBowler.setPlayerId(player.getPlayer_id());
            currentBowler.setName(player.getName());

            Optional<PlayerScoreSummary> playerScoreSummary = playerScoreSummaryRepository.findByMatchIdAndPlayerId(matchId, playerId);

            if (playerScoreSummary.isPresent())
            {
                currentBowler.setOvers(playerScoreSummary.get().getOversBowled());
                currentBowler.setWickets(playerScoreSummary.get().getWicketsTaken());
                currentBowler.setRunsGiven(playerScoreSummary.get().getRunsConceded());
            }
        }
        return currentBowler;
    }

    private void updateExistingScorecard(Scorecard scorecard, ConsumerRecord<UUID, MatchState> record)
    {
        String oversBowled = "";

        scorecard.setTeamId(record.value().getBatting_team());
        scorecard.setCurrentBatsman(record.value().getStrikerBatsman());
        scorecard.setNonStrikerBatsman(record.value().getNon_strikerBatsman());
        scorecard.setCurrentBowler(record.value().getBowler());

        if (!record.value().getEvent_type().equals(EventType.NO_BALL) || !record.value().getEvent_type().equals(EventType.WIDE))
        {
            oversBowled = String.valueOf(record.value().getOver()) + "." + String.valueOf(record.value().getBall());
            scorecard.setOversCompleted(oversBowled);
            scorecard.setTotalRuns(scorecard.getTotalRuns() + record.value().getRuns_scored());

            if (record.value().isWicket())
            {
                scorecard.setTotalWickets(scorecard.getTotalWickets() + 1);
            }
        }
        else
        {
            scorecard.setTotalRuns(scorecard.getTotalRuns() + 1);
        }
    }

    private Scorecard newScorecard(ConsumerRecord<UUID, MatchState> record)
    {
        Scorecard newScorecard = new Scorecard();

        String oversBowled = "";

        newScorecard.setMatchId(record.value().getMatch_id());
        newScorecard.setTeamId(record.value().getBatting_team());
        newScorecard.setTotalRuns(0);
        newScorecard.setTotalWickets(0);
        newScorecard.setOversCompleted("0");
        newScorecard.setCurrentBatsman(record.value().getStrikerBatsman());
        newScorecard.setNonStrikerBatsman(record.value().getNon_strikerBatsman());
        newScorecard.setCurrentBowler(record.value().getBowler());

        if (!record.value().getEvent_type().equals(EventType.NO_BALL) || !record.value().getEvent_type().equals(EventType.WIDE))
        {
            oversBowled = String.valueOf(record.value().getOver()) + "." + String.valueOf(record.value().getBall());
            newScorecard.setOversCompleted(oversBowled);
            newScorecard.setTotalRuns(newScorecard.getTotalRuns() + record.value().getRuns_scored());

            if (record.value().isWicket())
            {
                newScorecard.setTotalWickets(newScorecard.getTotalWickets() + 1);
            }
        }
        else
        {
            newScorecard.setTotalRuns(newScorecard.getTotalRuns() + 1);
        }

        return newScorecard;
    }

    @Override
    public MatchSummaryResponse matchSummary(UUID matchId)
    {
        Optional<Matches> matches = matchClient.getMatchStatus(matchId);
        MatchSummaryResponse matchSummaryResponse = new MatchSummaryResponse();

        if (matches.isPresent())
        {
            matchSummaryResponse.setMatchId(matchId);

            String firstTeamName = teamClient.getTeamByTeamId(matches.get().getFirstTeamId()).getTeamName();
            matchSummaryResponse.setTeam1(firstTeamName);

            String secondTeamName = teamClient.getTeamByTeamId(matches.get().getSecondTeamId()).getTeamName();
            matchSummaryResponse.setTeam2(secondTeamName);

            if (matches.get().getWinningTeamId().equals(matches.get().getFirstTeamId()))
            {
                matchSummaryResponse.setWinner(firstTeamName);
            }
            else
            {
                matchSummaryResponse.setWinner(secondTeamName);
            }

            matchSummaryResponse.setTotalRunsTeam1(scorecardRepository.findByMatchIdAndTeamId(matchId, matches.get().getFirstTeamId()).get().getTotalRuns());
            matchSummaryResponse.setTotalRunsTeam2(scorecardRepository.findByMatchIdAndTeamId(matchId, matches.get().getSecondTeamId()).get().getTotalRuns());
            matchSummaryResponse.setTopScorer(batsmanDetailsForMatchSummary(matchId));
            matchSummaryResponse.setBestBowler(bowlerDetailsForMatchSummary(matchId));
        }
        return matchSummaryResponse;
    }

    private CurrentBatsman batsmanDetailsForMatchSummary(UUID matchId)
    {
        CurrentBatsman currentBatsman = new CurrentBatsman();

        PlayerScoreSummary batsmanSummary = playerScoreSummaryRepository.findByMatchIdOrderByRunsScoredDescBallsFacedAsc(matchId);

        if (batsmanSummary != null)
        {
            currentBatsman.setPlayerId(batsmanSummary.getPlayerId());
            currentBatsman.setName(playerClient.getPlayer(batsmanSummary.getPlayerId()).getName());
            currentBatsman.setRuns(batsmanSummary.getRunsScored());
        }
        return currentBatsman;
    }

    private CurrentBowler bowlerDetailsForMatchSummary(UUID matchId)
    {
        CurrentBowler currentBowler = new CurrentBowler();

        PlayerScoreSummary bowlerSummary = playerScoreSummaryRepository.findByMatchIdOrderByWicketsTakenDescRunsConcededAsc(matchId);

        if (bowlerSummary != null)
        {
            currentBowler.setPlayerId(bowlerSummary.getPlayerId());
            currentBowler.setName(playerClient.getPlayer(bowlerSummary.getPlayerId()).getName());
            currentBowler.setWickets(bowlerSummary.getWicketsTaken());
        }
        return  currentBowler;
    }
}
