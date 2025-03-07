package com.scoring.dto;

import com.scoring.enums.MatchStatus;
import com.scoring.enums.TossDecision;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Matches
{
    private UUID matchId;
    private UUID firstTeamId;
    private UUID secondTeamId;
    private UUID tossWinnerId;
    private TossDecision tossDecision;
    private MatchStatus matchStatus;
    private UUID winningTeamId;
    private int overs;
    private String venue;
    private Date matchDate;
    private int winByWickets;
    private int winByRuns;
}
