package com.gullycricket.simulation.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gullycricket.simulation.dto.MatchState;
import com.gullycricket.simulation.enums.EventType;
import com.gullycricket.simulation.enums.EventTypeDeserializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class CustomRedisSerializer <T> implements RedisSerializer<T> {

    private final ObjectMapper objectMapper;
    private final Class<T> type;

    public CustomRedisSerializer(Class<T> type) {
        this.type = type;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.registerModule(new SimpleModule().addDeserializer(EventType.class, new EventTypeDeserializer()));
    }

    @Override
    public byte[] serialize(T t) throws SerializationException
    {
        try {
            return objectMapper.writeValueAsBytes(t);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error serializing object", e);
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        try {
            if(bytes != null) {
                return objectMapper.readValue(bytes, type);
            }
            return null;
        } catch (Exception e) {
            throw new SerializationException("Error deserializing object", e);
        }
    }
}
