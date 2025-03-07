package com.scoring.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Scorecard
{
    private UUID matchId;
    @Id
    private UUID teamId;
    private int totalRuns;
    private int totalWickets;
    private String oversCompleted;
    private UUID currentBatsman;
    private UUID nonStrikerBatsman;
    private UUID currentBowler;
}
