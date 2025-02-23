package com.teamservice.controller;

import com.teamservice.dto.PlayerIds;
import com.teamservice.entities.Player;
import com.teamservice.entities.Team;
import com.teamservice.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/team")
public class TeamController
{
    @Autowired
    private TeamService teamService;

    @GetMapping
    public List<Team> getAllTeam()
    {
        return teamService.getTeams();
    }

    @GetMapping("/{teamId}")
    public Team getTeamByTeamId(@PathVariable UUID teamId)
    {
        return teamService.getTeamByTeamId(teamId);
    }

    @PostMapping
    public Team createTeam(@RequestBody Team team)
    {
        return teamService.createTeam(team);
    }

    @PostMapping("/assignPlayerToTeam/{teamId}")
    public Team addPlayerToTeam(@PathVariable UUID teamId, @RequestBody PlayerIds playerIds)
    {
        return teamService.addPlayerToTeam(teamId, playerIds);
    }

    @PostMapping("/removePlayerFromTeam/{teamId}")
    public Team removePlayerFromTeam(@PathVariable UUID teamId, @RequestBody PlayerIds playerIds)
    {
        return teamService.removePlayerFromTeam(teamId, playerIds);
    }

    @PutMapping("/updateTeam/{teamId}")
    public Team updateTeam(@PathVariable UUID teamId, @RequestBody Team updatedTeam)
    {
        return teamService.updateTeam(teamId, updatedTeam);
    }

}
