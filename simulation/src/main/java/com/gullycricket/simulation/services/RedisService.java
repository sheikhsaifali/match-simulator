package com.gullycricket.simulation.services;

import com.gullycricket.simulation.dto.MatchState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, MatchState> redisTemplate;
    @Autowired
    private ProducerService producerService;

    public void saveData(String matchId, MatchState matchState)
    {
        redisTemplate.opsForValue().set(matchId, matchState);
        producerService.publishBallEvent(matchState);
    }

    public MatchState getData(String matchId)
    {
        return redisTemplate.opsForValue().get(matchId);
    }
}
