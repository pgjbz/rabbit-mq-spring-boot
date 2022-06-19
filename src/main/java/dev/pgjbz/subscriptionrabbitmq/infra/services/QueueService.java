package dev.pgjbz.subscriptionrabbitmq.infra.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.pgjbz.subscriptionrabbitmq.domain.exceptions.DataNotFoundException;
import dev.pgjbz.subscriptionrabbitmq.domain.models.Subscription;
import dev.pgjbz.subscriptionrabbitmq.domain.ports.services.SubscriptionService;
import dev.pgjbz.subscriptionrabbitmq.infra.configuration.RabbitMQConfig;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class QueueService {

    private final Logger log = LoggerFactory.getLogger(QueueService.class);

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig rabbitMQConfig;
    private final SubscriptionService subscriptionService;
    private final ObjectMapper objectMapper;

    public QueueService(RabbitTemplate rabbitTemplate,
            RabbitMQConfig rabbitMQConfig,
            SubscriptionService subscriptionService,
            ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQConfig = rabbitMQConfig;
        this.subscriptionService = subscriptionService;
        this.objectMapper = objectMapper;
    }

    public void publishMessage(String message) {
        Mono.just(new Message(message.getBytes()))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(m -> {
                    log.info("publishing message {}", message);
                    rabbitTemplate.send(rabbitMQConfig.exchange(), rabbitMQConfig.routingKey(), m);
                    return Mono.empty();
                }).subscribe().dispose();
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listenQueue(String message) {
        try {
            var subscription = objectMapper.readValue(message, Subscription.class);
            executeAction(message, subscription);
        } catch (JsonProcessingException e) {
            log.error("error on deserialize message {}", message, e);
        }
    }

    private void executeAction(String message, Subscription subscription) {
        try {
            switch (subscription.status()) {
                case ACTIVED -> {
                    log.info("create new subscription {}", message);
                    subscriptionService.createSubscription(subscription);
                    break;
                }
                case DESATIVED, RESTARTED -> {
                    log.info("update subscription {}", message);
                    subscriptionService.updateSubscription(subscription);
                }
                default -> {
                    log.warn("No action");
                }
            }
        } catch (DataNotFoundException e) {
            subscriptionService.createSubscription(subscription);
        }
    }

}
