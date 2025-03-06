package com.gullycricket.match_service.dto;

import java.util.UUID;

public record PlayingEleven(UUID matchId,
                            UUID teamId,
                            PlayerIds players) {

}
