package com.teamservice.service;

import com.teamservice.entities.Team;

import java.util.List;
import java.util.UUID;

public interface TeamService
{
    List<Team> getTeams();

    Team getTeamByTeamId(UUID teamId);

    Team createTeam(Team team);

    int addPlayerToTeam(UUID playerId);

    int removePlayerFromTeam(UUID playerId);

    String updateTeam(UUID teamId, Team updatedTeam);

    String deleteTeam(UUID teamId);
}
