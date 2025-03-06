package com.gullycricket.match_service.dto;

import java.util.UUID;

public record Player(UUID player_id,
                     String name,
                     int age,
                     int batting_skill,
                     int bowling_skill,
                     int fielding_skill,
                     UUID teamId,
                     int matches_played,
                     int runs_scored,
                     int wickets_taken,
                     String roles) {
}
