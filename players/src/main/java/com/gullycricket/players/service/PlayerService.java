package com.gullycricket.players.service;

import com.gullycricket.players.model.Player;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface PlayerService {
    List<Player> getPlayers();
    Player getPlayer(UUID playerId);
    String createPlayer(Player player);
    String updatePlayer(Player player, UUID player_id);
    String assignPlayer(Player player, UUID player_id);
}
