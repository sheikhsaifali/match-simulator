package com.gullycricket.match_service.dto;

import java.util.UUID;

public record MatchStatus(UUID matchId, String status) {
}
