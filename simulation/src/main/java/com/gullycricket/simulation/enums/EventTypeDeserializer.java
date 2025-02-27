package com.gullycricket.simulation.enums;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class EventTypeDeserializer extends JsonDeserializer<EventType> {
    @Override
    public EventType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();
        return EventType.fromValue(value);
    }
}

