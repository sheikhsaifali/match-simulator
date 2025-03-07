package com.scoring.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scoring.dto.MatchState;
import org.apache.kafka.common.serialization.Deserializer;

public class MatchStateDesirializer implements Deserializer<MatchState>
{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public MatchState deserialize(String s, byte[] data)
    {
        try
        {
            if (data != null)
            {
                return objectMapper.readValue(data, MatchState.class);
            }
            return null;
        }
        catch (Exception ex)
        {
            throw new RuntimeException("Error deserializing MatchState", ex);
        }
    }
}
