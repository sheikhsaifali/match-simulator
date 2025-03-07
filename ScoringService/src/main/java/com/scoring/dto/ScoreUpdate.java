package com.scoring.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ScoreUpdate implements Serializable
{
    private UUID matchId;
    private int over;
    private int ball;
    private String batsman;
    private String bowler;
    private int runs;
    private int totalRuns;
    private int wickets;
}
