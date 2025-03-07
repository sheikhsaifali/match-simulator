package com.scoring.dto;

import com.scoring.enums.EventType;
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
    private UUID batting_team;
    private UUID bowling_team;
    private int ball;
    private int over;
    private UUID strikerBatsman;
    private UUID non_strikerBatsman;
    private UUID bowler;
    private int runs_scored;
    private boolean wicket;
    @Enumerated(EnumType.STRING)
    private EventType event_type;
    private Date timestamp;
}
