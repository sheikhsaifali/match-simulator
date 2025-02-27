package com.gullycricket.simulation.dto;

import com.gullycricket.simulation.enums.EventType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchState implements Serializable {
     private UUID match_id;
     private String batting_team;
     private String bowling_team;
     private int ball;
     private int over;
     private String strikerBatsman;
     private String non_strikerBatsman;
     private String bowler;
     private int runs_scored;
     private boolean wicket;
     @Enumerated(EnumType.STRING)
     private EventType event_type;
     private Date timestamp;
}
