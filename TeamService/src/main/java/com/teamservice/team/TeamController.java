package com.teamservice.team;

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

    @PostMapping
    public Team createTeam(@RequestBody Team team)
    {
        return teamService.createTeam(team);
    }

    @GetMapping
    public List<Team> getAllTeams()
    {
        return teamService.getTeams();
    }

    @GetMapping("/{teamId}")
    public Team getTeamById(@PathVariable UUID teamId)
    {
        return teamService.getTeamByTeamId(teamId);
    }

}
