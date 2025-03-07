package com.scoring.controller;

import com.scoring.dto.BallByBallSummary;
import com.scoring.dto.DetailedScorecardResponse;
import com.scoring.dto.MatchSummaryResponse;
import com.scoring.entities.PlayerScoreSummary;
import com.scoring.service.PlayerScoreSummaryService;
import com.scoring.service.RedisService;
import com.scoring.service.ScorecardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/matchscore")
public class MatchScoreController
{
    @Autowired
    private ScorecardService scorecardService;

    @Autowired
    private PlayerScoreSummaryService playerScoreSummaryService;

    @Autowired
    private RedisService redisService;

    @GetMapping("/{matchId}/score")
    public DetailedScorecardResponse showDetailedScorecard(@PathVariable UUID matchId)
    {
        return scorecardService.displayDetailedScorecard(matchId);
    }

    @GetMapping("/{matchId}/balls")
    public List<BallByBallSummary> ballByBallSummary(@PathVariable UUID matchId)
    {
        return redisService.getBallByBallSummary(matchId);
    }

    @GetMapping("/{matchId}/players/{playerId}/performance")
    public PlayerScoreSummary getPlayerPerformance(@PathVariable UUID matchId, @PathVariable UUID playerId)
    {
        return playerScoreSummaryService.getPlayerPerformance(matchId, playerId);
    }

    @GetMapping("/{matchId}/summary")
    public MatchSummaryResponse matchSummary(@PathVariable UUID matchId)
    {
        return scorecardService.matchSummary(matchId);
    }
}
