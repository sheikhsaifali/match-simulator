package com.scoring.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.scoring.converter.EventTypeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonDeserialize(using = EventTypeDeserializer.class)
public enum EventType {
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    SIX("6"),
    NO_BALL("No-Ball"),
    WIDE("Wide"),
    LEG_BYES("Leg-Byes");

    private final String value;

    @JsonCreator
    public static EventType fromValue(String value) {
        for (EventType eventType : EventType.values()) {
            if (eventType.value.equalsIgnoreCase(value)) {
                return eventType;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
