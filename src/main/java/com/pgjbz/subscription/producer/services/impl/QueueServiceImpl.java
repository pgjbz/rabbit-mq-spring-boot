package com.pgjbz.subscription.producer.services.impl;

import com.pgjbz.subscription.configuration.RabbitMQConfig;
import com.pgjbz.subscription.producer.services.QueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Log4j2
@RequiredArgsConstructor
public class QueueServiceImpl implements QueueService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(String message) {
        if(!StringUtils.hasLength(message)) throw new IllegalArgumentException("Message cannot be null or empty");
        try {
            log.info("Sending message {} to queue", message);
            rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME, "rabbit.messages", message);
        } catch (AmqpException e) {
            log.info("Error on sending message {}", message);
        }
    }

}
