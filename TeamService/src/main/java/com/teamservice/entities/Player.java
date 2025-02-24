package com.teamservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Player {

    private UUID player_id;
    private String name;
    private int age;
    private int batting_skill;
    private int bowling_skill;
    private int fielding_skill;
    private UUID teamId;
    private int matches_played;
    private int runs_scored;
    private int wickets_taken;
    private String roles;
}
