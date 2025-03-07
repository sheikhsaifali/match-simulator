package com.scoring.service;

import com.scoring.dto.BallByBallSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService
{
    private static final String REDIS_PREFIX = "over_summary:";

    @Autowired
    private RedisTemplate<String, BallByBallSummary> redisTemplate;

    public void saveBallSummary(UUID matchId, BallByBallSummary ballByBallSummary)
    {
        String key = REDIS_PREFIX + matchId;
        redisTemplate.opsForList().rightPush(key, ballByBallSummary);
        redisTemplate.opsForList().trim(key, -6, -1);
        redisTemplate.expire(key, 5, TimeUnit.MINUTES);
    }

    public List<BallByBallSummary> getBallByBallSummary(UUID matchId)
    {
        String key = REDIS_PREFIX + matchId;
        return redisTemplate.opsForList().range(key, 0, -1);
    }
}
