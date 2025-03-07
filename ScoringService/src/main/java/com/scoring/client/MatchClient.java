package com.scoring.client;

import com.scoring.dto.Matches;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.UUID;

@FeignClient(url = "http://localhost:8084/matches", value = "Match-Client")
public interface MatchClient
{
    @GetMapping("/{matchId}")
    Optional<Matches> getMatchStatus(@PathVariable UUID matchId);
}
