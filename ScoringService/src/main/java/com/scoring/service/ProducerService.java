package com.scoring.service;

import com.scoring.dto.ScoreUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProducerService
{
    private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);

    @Autowired
    private KafkaTemplate<UUID, ScoreUpdate> kafkaTemplate;

    public void publishMatchEvent(ScoreUpdate message)
    {
        kafkaTemplate.send("match-state-update", message);
        logger.info(message.toString());
    }

}
