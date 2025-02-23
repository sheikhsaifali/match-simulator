package com.teamservice.service;

import com.teamservice.dto.PlayerIds;
import com.teamservice.entities.Player;
import com.teamservice.entities.Team;

import java.util.List;
import java.util.UUID;

public interface TeamService
{
    List<Team> getTeams();

    Team getTeamByTeamId(UUID teamId);

    Team createTeam(Team team);

    Team addPlayerToTeam(UUID teamId, PlayerIds playerIds);

    Team removePlayerFromTeam(UUID teamId, PlayerIds playerIds);

    Team updateTeam(UUID teamId, Team updatedTeam);

    String deleteTeam(UUID teamId);
}
