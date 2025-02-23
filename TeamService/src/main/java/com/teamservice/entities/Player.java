package com.teamservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
}
