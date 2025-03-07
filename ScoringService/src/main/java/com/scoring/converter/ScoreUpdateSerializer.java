package com.scoring.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.scoring.dto.ScoreUpdate;
import org.apache.kafka.common.serialization.Serializer;

public class ScoreUpdateSerializer implements Serializer<ScoreUpdate>
{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String s, ScoreUpdate scoreUpdate)
    {
        try
        {
            objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            return objectMapper.writeValueAsBytes(scoreUpdate);
        }
        catch (Exception ex)
        {
            throw new RuntimeException("Error serializing ScoreUpdate", ex);
        }
    }
}
