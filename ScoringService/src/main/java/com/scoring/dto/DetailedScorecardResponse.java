package com.scoring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailedScorecardResponse
{
    private UUID matchId;
    private String battingTeam;
    private int totalRuns;
    private int totalWickets;
    private String totalOvers;
    private CurrentBatsmen currentBatsmen;
    private CurrentBowler currentBowler;
}
