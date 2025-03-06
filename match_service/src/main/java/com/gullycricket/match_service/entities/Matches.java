package com.gullycricket.match_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Matches {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID matchId;
    private UUID firstTeamId;
    private UUID secondTeamId;
    private UUID tossWinnerId;
    @Enumerated(EnumType.STRING)
    private TossDecision tossDecision;
    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;
    private UUID winningTeamId;
    private int overs;
    private String venue;
    private Date matchDate;
    private int winByWickets;
    private int winByRuns;
}
