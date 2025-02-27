package com.gullycricket.simulation.config;

import com.gullycricket.simulation.converter.MatchStateSerializer;
import com.gullycricket.simulation.dto.MatchState;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic ballEventTopic()
    {
        return new NewTopic("ball.event",3,(short)1);
    }
    @Bean
    public ProducerFactory<String, MatchState> producerFactory()
    {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MatchStateSerializer.class.getName());
        return new DefaultKafkaProducerFactory<>(config);
    }
    @Bean
    public KafkaTemplate<String, MatchState> kafkaTemplate()
    {
        return new KafkaTemplate<>(producerFactory());
    }
}
