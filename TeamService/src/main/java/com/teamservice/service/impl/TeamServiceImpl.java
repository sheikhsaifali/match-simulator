package com.teamservice.service.impl;

import com.teamservice.client.PlayerClient;
import com.teamservice.entities.Team;
import com.teamservice.repositories.TeamRepository;
import com.teamservice.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        /*List<Team> teamListWithPlayers = teamList.stream().map(team -> {
            team.setPlayerList(playerClient.getPlayersByTeamId(team.getTeamId()));
            return team;
        }).collect(Collectors.toList());*/

        return teamList;
    }

    @Override
    public Team getTeamByTeamId(UUID teamId)
    {
        Team team = teamRepository.findById(teamId).orElseThrow(()-> new RuntimeException("Team not found!!"));
        //team.setPlayerList(playerClient.getPlayersByTeamId(teamId));

        return team;
    }

    @Override
    public Team createTeam(Team team)
    {
        Team savedTeam = teamRepository.save(team);

        if (team.getPlayerList() != null && !team.getPlayerList().isEmpty())
        {
            //Add code to write players to player db
        }
        return savedTeam;
    }

    @Override
    public int addPlayerToTeam(UUID playerId) {
        return 0;
    }

    @Override
    public int removePlayerFromTeam(UUID playerId) {
        return 0;
    }

    @Override
    public String updateTeam(UUID teamId, Team updatedTeam)
    {
        Team team = this.getTeamByTeamId(teamId);

        if (team != null && updatedTeam != null)
        {
            team.setTeamName(updatedTeam.getTeamName());
            team.setMatchesLost(updatedTeam.getMatchesLost());
            team.setMatchesWon(updatedTeam.getMatchesWon());

            teamRepository.save(team);

            if (team.getPlayerList() != null && !team.getPlayerList().isEmpty())
            {
                //update player table by calling player service
            }
        }
        return "";
    }

    @Override
    public String deleteTeam(UUID teamId) {
        return "";
    }
}
