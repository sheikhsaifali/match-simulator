package com.teamservice.client;


import com.teamservice.entities.Player;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "TEAM-SERVICE")
public interface PlayerClient
{
    @GetMapping("/team/player/{playerId}")
    List<Player> getPlayersByTeamId(@PathVariable UUID playerId);
}

