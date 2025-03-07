package com.scoring.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerScoreSummary
{
    private UUID matchId;
    @Id
    private UUID playerId;
    private int runsScored;
    private int ballsFaced;
    private int fours;
    private int sixes;
    private String oversBowled;
    private int wicketsTaken;
    private int runsConceded;
}
