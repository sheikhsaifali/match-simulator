package com.scoring.client;

import com.scoring.dto.Player;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(url = "http://localhost:8082/api/v1", value = "Player-Client")
public interface PlayerClient
{
    @GetMapping("/players/{playerId}")
    Player getPlayer(@PathVariable UUID playerId);
}
