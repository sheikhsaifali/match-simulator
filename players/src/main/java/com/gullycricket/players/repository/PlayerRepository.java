package com.gullycricket.players.repository;

import com.gullycricket.players.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {
    List<Player> findByTeamId(UUID team_id);
}
