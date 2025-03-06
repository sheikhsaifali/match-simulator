package com.gullycricket.match_service.controllers;

import com.gullycricket.match_service.dto.MatchStatus;
import com.gullycricket.match_service.dto.Team;
import com.gullycricket.match_service.dto.Toss;
import com.gullycricket.match_service.entities.Matches;
import com.gullycricket.match_service.services.Match_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/matches")
public class MatchServiceController {

    @Autowired
    private Match_Service service;

    @PostMapping("/")
    public MatchStatus createMatch(@RequestBody Matches match)
    {
        return service.createMatch(match);
    }

    @PostMapping("/{match_id}/assign-Playing-xi")
    public List<Team> assignPlayeingEleven(@PathVariable UUID match_id)
    {
        return service.assignPlayeingEleven(match_id);
    }

    @PostMapping("/{match_id}/toss")
    public String conductToss(@PathVariable UUID match_id, @RequestBody Toss toss)
    {
        return service.conductToss(toss.decision(), match_id, toss.teamId());
    }

    @PatchMapping("/{match_id}/start")
    public String startMatch(@PathVariable UUID match_id)
    {
        return service.startMatch(match_id);
    }

    @GetMapping("/{match_id}")
    public Optional<Matches> getMatchStatus(@PathVariable UUID match_id)
    {
        return service.getMatchStatus(match_id);
    }

    @GetMapping("/{match_id}/live-score")// scoring service
    public Optional<Matches> fetchLiveScore(@PathVariable UUID match_id)
    {
        return service.getMatchStatus(match_id);
    }

    @PatchMapping("/{match_id}/end")
    public String endMatchResult(@PathVariable UUID match_id, @RequestBody Matches match)
    {
        return service.endMatchResult(match_id, match);
    }

    @GetMapping("/history")
    public List<Matches> getMatchHistory()
    {
        return service.getMatchHistory();
    }
}
