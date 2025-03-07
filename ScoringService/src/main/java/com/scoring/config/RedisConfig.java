package com.scoring.config;

import com.scoring.converter.CustomRedisSerializer;
import com.scoring.dto.BallByBallSummary;
import com.scoring.dto.ScoreUpdate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig
{
    @Bean
    public RedisTemplate<String, BallByBallSummary> redisTemplate(RedisConnectionFactory redisConnectionFactory)
    {
        RedisTemplate<String, BallByBallSummary> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new CustomRedisSerializer<>(BallByBallSummary.class));
        return template;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory()
    {
        return new LettuceConnectionFactory();
    }
}
