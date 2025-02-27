package com.gullycricket.simulation.config;

import com.gullycricket.simulation.converter.CustomRedisSerializer;
import com.gullycricket.simulation.dto.MatchState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisConnectionFactory redisConnectionFactory()
    {
        return  new LettuceConnectionFactory();
    }
    @Bean
    public RedisTemplate<String, MatchState> redisTemplate(RedisConnectionFactory redisConnectionFactory)
    {
        RedisTemplate<String, MatchState> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new CustomRedisSerializer<>(MatchState.class));
        return template;
    }
}
