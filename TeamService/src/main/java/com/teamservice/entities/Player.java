package com.teamservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Player
{
    private Long playerId;
    private String playerName;
    private int playerAge;
    private String playerRole;
    private int battingSkill;
    private int bowlingSkill;
    private int fieldingSkill;
    private UUID teamId;
    private int matchesPlayed;
    private int runsScored;
    private int wicketsTaken;
}
