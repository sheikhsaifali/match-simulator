package com.gullycricket.players.service;

import com.gullycricket.players.model.Player;
import com.gullycricket.players.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player getPlayer(UUID playerId) {
        return playerRepository.findById(playerId).orElseThrow(()->new RuntimeException("Player Not Found!!!"));
    }

    @Override
    public String createPlayer(Player player) {
        Player playerSave = playerRepository.save(player);
        return (playerSave != null) ? "Saved":"Not Saved";
    }

    @Override
    public String updatePlayer(Player player, UUID player_id) {
        Player playerSaved = getPlayer(player_id);
        if(playerSaved != null && player != null)
        {
            playerSaved.setAge(player.getAge());
            playerSaved.setBatting_skill(player.getBatting_skill());
            playerSaved.setFielding_skill(player.getFielding_skill());
            playerSaved.setBowling_skill(player.getBowling_skill());
            playerSaved.setName(player.getName());
            playerSaved.setMatches_played(player.getMatches_played());
            playerSaved.setRuns_scored(player.getRuns_scored());
            playerSaved.setWickets_taken(player.getWickets_taken());
            playerRepository.save(playerSaved);
        }
        return (playerSaved != null) ? "Saved":"Not Saved";
    }

    @Override
    public String assignPlayer(Player player, UUID player_id) {
        Player playerSaved = getPlayer(player_id);
        if(playerSaved != null && player != null)
        {
            playerSaved.setTeamId(player.getTeamId());
            playerRepository.save(playerSaved);
        }
        return (playerSaved != null) ? "Saved":"Not Saved";
    }

    @Override
    public List<Player> getPlayersByTeamId(UUID team_id) {
        return playerRepository.findByTeamId(team_id);
    }
}
