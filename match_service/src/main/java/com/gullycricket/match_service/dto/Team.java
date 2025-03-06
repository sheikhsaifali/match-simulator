package com.gullycricket.match_service.dto;

import java.util.List;
import java.util.UUID;

public record Team(UUID teamId, String teamName, List<Player> playerList, PlayerIds playerIds, int matchesWon, int matchesLost) {
}
