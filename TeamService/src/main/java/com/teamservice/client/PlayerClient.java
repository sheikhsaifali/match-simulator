package com.teamservice.client;


import com.teamservice.config.FeignConfig;
import com.teamservice.entities.Player;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(url = "http://localhost:8090/api/v1", value = "Players-Client",  configuration = FeignConfig.class)
public interface PlayerClient
{
    @GetMapping("players/team/{team_id}")
    List<Player> getPlayersByTeamId(@PathVariable UUID team_id);

    @RequestMapping(method = RequestMethod.PATCH, value = "/players/{player_id}")
    ResponseEntity<HttpStatus> assignPlayer(@RequestBody Player player, @PathVariable UUID player_id);
}

