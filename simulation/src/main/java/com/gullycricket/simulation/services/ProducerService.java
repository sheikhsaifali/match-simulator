package com.gullycricket.simulation.services;

import com.gullycricket.simulation.dto.MatchState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class ProducerService {
    private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);
    @Autowired
    private KafkaTemplate<String, MatchState> kafkaTemplate;

    public void publishBallEvent(MatchState message)
    {
        kafkaTemplate.send("ball.event", message);
        logger.info(message.toString());
    }
}
