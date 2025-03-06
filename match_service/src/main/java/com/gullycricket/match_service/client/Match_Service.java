package com.gullycricket.match_service.client;

import com.gullycricket.match_service.dto.Team;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(url = "http://localhost:8091/team", value = "Team-Client")
public interface Match_Service {
     @GetMapping("/{teamId}")
     Team getTeamByTeamId(@PathVariable UUID teamId);
}
