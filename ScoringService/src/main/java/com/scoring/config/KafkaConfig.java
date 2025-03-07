package com.scoring.config;

import com.scoring.converter.MatchStateDesirializer;
import com.scoring.converter.ScoreUpdateSerializer;
import com.scoring.dto.MatchState;
import com.scoring.dto.ScoreUpdate;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class KafkaConfig
{
    @Bean
    public NewTopic matchStateUpdate()
    {
        return new NewTopic("match-state-update", 3,(short)1);
    }

    @Bean
    public ConsumerFactory<UUID, MatchState> consumerFactory()
    {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "ball-event-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class.getName());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MatchStateDesirializer.class.getName());
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ProducerFactory<UUID, ScoreUpdate> producerFactory()
    {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class.getName());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ScoreUpdateSerializer.class.getName());
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<UUID, ScoreUpdate> kafkaTemplate()
    {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<UUID, MatchState> concurrentKafkaListenerContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<UUID, MatchState> consumerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        consumerFactory.setConsumerFactory(consumerFactory());
        return consumerFactory;
    }
}
