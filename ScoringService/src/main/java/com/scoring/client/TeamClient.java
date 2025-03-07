package com.scoring.client;

import com.scoring.dto.Team;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(url = "http://localhost:8081/team", value = "Team-Client")
public interface TeamClient
{
    @GetMapping("/{teamId}")
    Team getTeamByTeamId(@PathVariable UUID teamId);
}
