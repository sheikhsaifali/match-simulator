package com.gullycricket.match_service.services;

import com.gullycricket.match_service.dto.Team;
import com.gullycricket.match_service.entities.Matches;
import com.gullycricket.match_service.entities.MatchStatus;
import com.gullycricket.match_service.entities.TossDecision;
import com.gullycricket.match_service.repositories.Match_Service_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class Match_Service {
    @Autowired
    private Match_Service_Repo match_service_repo;
    @Autowired
    private com.gullycricket.match_service.client.Match_Service match_service;
    public com.gullycricket.match_service.dto.MatchStatus createMatch(Matches match)
    {
        Matches match1 = match_service_repo.save(match);
        return new com.gullycricket.match_service.dto.MatchStatus(match1.getMatchId(), match1.getMatchStatus().toString());
    }

    public List<Team> assignPlayeingEleven(UUID match_id)
    {
        Optional<Matches> match1 = getMatchStatus(match_id);
        ArrayList<Team> team = new ArrayList<>();
        if (match1 != null)
        {
            if(match1.get().getFirstTeamId() != null) {
                team.add(match_service.getTeamByTeamId(match1.get().getFirstTeamId()));
            }
            if(match1.get().getSecondTeamId() != null) {
                team.add(match_service.getTeamByTeamId(match1.get().getSecondTeamId()));
            }
        }
        return team;
    }

    public String conductToss(String decision, UUID match_id, UUID teamId)
    {
        Optional<Matches> match = getMatchStatus(match_id);
        Team team = null;
        if (match != null)
        {
            TossDecision tossDecision = TossDecision.valueOf(decision);
            match.get().setTossDecision(tossDecision);
            match.get().setTossWinnerId(teamId);
            team = match_service.getTeamByTeamId(teamId);
            match_service_repo.save(match.get());
        }
        return "Toss won by "+ team.teamName() +", elected to "+ decision+" first !!!";
    }

    public String startMatch(UUID match_id)
    {
        Optional<Matches> match = getMatchStatus(match_id);
        if (match != null)
        {
            MatchStatus matchStatus = MatchStatus.valueOf("In_Progress");
            match.get().setMatchStatus(matchStatus);
            match_service_repo.save(match.get());
        }
        return "In Progress";
    }

    public Optional<Matches> getMatchStatus(UUID match_id)
    {
        return match_service_repo.findById(match_id);
    }

    public String endMatchResult(UUID match_id, Matches match)
    {
        Optional<Matches> match1 = getMatchStatus(match_id);
        if (match1 != null)
        {
            match1.get().setWinningTeamId(match.getWinningTeamId());
            match1.get().setMatchStatus(match.getMatchStatus());
            match1.get().setWinByRuns(match.getWinByRuns());
            match1.get().setWinByWickets(match.getWinByWickets());
            match_service_repo.save(match1.get());
        }
        if (match.getWinByRuns()>0)
        {
            return match1.get().getWinningTeamId()+" won by"+ match.getWinByRuns()+" runs.";
        }
        return match1.get().getWinningTeamId()+" won by"+ match.getWinByWickets()+" wickets.";
    }

    public List<Matches> getMatchHistory()
    {
        return match_service_repo.findAll();
    }
}
