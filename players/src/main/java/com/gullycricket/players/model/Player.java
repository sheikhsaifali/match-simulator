package com.gullycricket.players.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Player {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID player_id;
    private String name;
    private int age;
    private int batting_skill;
    private int bowling_skill;
    private int fielding_skill;
    @Column(updatable = true, nullable = true)
    private UUID team_id;
    private int matches_played;
    private int runs_scored;
    private int wickets_taken;
}
