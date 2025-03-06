package com.gullycricket.match_service.entities;

import java.util.Date;
import java.util.UUID;

public class MatchEvent {
    private UUID eventId;
    private UUID matchId;
    private EventType eventType;
    private String description;
    private Date timestamp;
}
