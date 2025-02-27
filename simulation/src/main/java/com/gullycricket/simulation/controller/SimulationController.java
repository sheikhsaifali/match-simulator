package com.gullycricket.simulation.controller;

import com.gullycricket.simulation.dto.MatchState;
import com.gullycricket.simulation.services.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/simulation")
public class SimulationController {
    @Autowired
    private RedisService service;

    //Receives real-time input for a ball event.
    @PostMapping("/{match_id}/ball-event")
    public String updateLiveBallEvent(@RequestBody MatchState matchState, @PathVariable String match_id)
    {
        service.saveData(match_id,matchState);
        return "Data Saved !!!";
    }

    //Fetches the latest match state.
    @GetMapping("/{match_id}/status")
    public MatchState currentMatchState(@PathVariable String match_id)
    {
        MatchState match = service.getData(match_id);
        return match;
    }
}
