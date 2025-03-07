package com.scoring.dto;

import com.scoring.enums.EventType;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BallByBallSummary implements Serializable
{
    private int overNumber;
    private int ballNumber;
    private String batsman;
    private String bowler;
    private EventType eventType;
}
