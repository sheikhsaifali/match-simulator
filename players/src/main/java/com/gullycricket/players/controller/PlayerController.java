package com.gullycricket.players.controller;

import com.gullycricket.players.model.Player;
import com.gullycricket.players.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/")
public class PlayerController {

    @Autowired
    PlayerService service;
    @GetMapping("players")
    public List<Player> getPlayers()
    {
        return service.getPlayers();
    }
    @GetMapping("players/{player_id}")
    public Player getPlayer(@PathVariable UUID player_id)
    {
        return service.getPlayer(player_id);
    }
    @PostMapping("players")
    public ResponseEntity<HttpStatus> createPlayer(@RequestBody Player player)
    {
        String response = service.createPlayer(player);
        return response.equals("Saved")?ResponseEntity.ok(HttpStatus.CREATED):ResponseEntity.ok(HttpStatus.EXPECTATION_FAILED);
    }
    @PutMapping("players/{player_id}")
    public ResponseEntity<HttpStatus> updatePlayer(@RequestBody Player player, @PathVariable UUID player_id)
    {
        String response = service.updatePlayer(player, player_id);
        return response.equals("Saved")?ResponseEntity.ok(HttpStatus.OK):ResponseEntity.ok(HttpStatus.EXPECTATION_FAILED);
    }
    @PatchMapping("players/{player_id}")
    public ResponseEntity<HttpStatus> assignPlayer(@RequestBody Player player, @PathVariable UUID player_id)
    {
        String response = service.assignPlayer(player, player_id);
        return response.equals("Saved")?ResponseEntity.ok(HttpStatus.OK):ResponseEntity.ok(HttpStatus.EXPECTATION_FAILED);
    }
}
