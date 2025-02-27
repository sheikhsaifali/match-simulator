package com.teamservice.service.impl;

import com.teamservice.client.PlayerClient;
import com.teamservice.dto.PlayerIds;
import com.teamservice.entities.Player;
import com.teamservice.entities.Team;
import com.teamservice.repositories.TeamRepository;
import com.teamservice.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService
{
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerClient playerClient;

    @Override
    public List<Team> getTeams()
    {
        List<Team> teamList = teamRepository.findAll();

        List<Team> teamListWithPlayers = teamList.stream().map(team -> {
            team.setPlayerList(playerClient.getPlayersByTeamId(team.getTeamId()));
            return team;
        }).collect(Collectors.toList());

        return teamList;
    }

    @Override
    public Team getTeamByTeamId(UUID teamId)
    {
        Team team = teamRepository.findById(teamId).orElseThrow(()-> new RuntimeException("Team not found!!"));
        team.setPlayerList(playerClient.getPlayersByTeamId(teamId));

        return team;
    }

    @Override
    public Team createTeam(Team team)
    {
        Team savedTeam = teamRepository.save(team);

        if (savedTeam != null && team.getPlayerIds() != null)
        {
            Player newPlayerAddedToTeam = new Player();
            
            for (UUID playerId : team.getPlayerIds().getPlayerIds())
            {
                newPlayerAddedToTeam.setTeamId(savedTeam.getTeamId());
                playerClient.assignPlayer(newPlayerAddedToTeam, playerId);
            }
            return this.getTeamByTeamId(savedTeam.getTeamId());
        }
        return savedTeam;
    }

    @Override
    public Team addPlayerToTeam(UUID teamId, PlayerIds playerIds)
    {
        Player player = new Player();

        if (playerIds != null && playerIds.getPlayerIds() != null && !playerIds.getPlayerIds().isEmpty())
        {
            for (UUID playerId : playerIds.getPlayerIds())
            {
                player.setTeamId(teamId);
                playerClient.assignPlayer(player, playerId);
            }
        }
        return this.getTeamByTeamId(teamId);
    }

    @Override
    public Team removePlayerFromTeam(UUID teamId, PlayerIds playerIds)
    {
        Player player = new Player();

        if (playerIds != null && playerIds.getPlayerIds() != null && !playerIds.getPlayerIds().isEmpty())
        {
            for (UUID playerId : playerIds.getPlayerIds())
            {
                player.setTeamId(teamId);
                playerClient.assignPlayer(player, null);
            }
        }
        return this.getTeamByTeamId(teamId);
    }

    @Override
    public Team updateTeam(UUID teamId, Team updatedTeam)
    {
        Team team = this.getTeamByTeamId(teamId);

        if (team != null && updatedTeam != null)
        {
            team.setTeamName(updatedTeam.getTeamName());
            team.setMatchesLost(updatedTeam.getMatchesLost());
            team.setMatchesWon(updatedTeam.getMatchesWon());

            teamRepository.save(team);

            if (team.getPlayerIds() != null && team.getPlayerIds().getPlayerIds() != null
            && !team.getPlayerIds().getPlayerIds().isEmpty())
            {
                this.addPlayerToTeam(team.getTeamId(), team.getPlayerIds());
            }
        }
        return this.getTeamByTeamId(team.getTeamId());
    }

    @Override
    public String deleteTeam(UUID teamId)
    {
        Team team = this.getTeamByTeamId(teamId);
        List<UUID> playerIdList = new ArrayList<>();

        for (Player player : team.getPlayerList())
        {
            playerIdList.add(player.getPlayer_id());
        }

        PlayerIds playerIds = new PlayerIds();
        playerIds.setPlayerIds(playerIdList);

        this.removePlayerFromTeam(teamId, playerIds);
        teamRepository.delete(team);

        return "Team Deleted Successfully!!";
    }
}
