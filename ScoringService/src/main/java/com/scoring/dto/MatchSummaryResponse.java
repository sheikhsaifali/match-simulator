package com.scoring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchSummaryResponse
{
    private UUID matchId;
    private String team1;
    private String team2;
    private String winner;
    private int totalRunsTeam1;
    private int totalRunsTeam2;
    private CurrentBatsman topScorer;
    private CurrentBowler bestBowler;
}
