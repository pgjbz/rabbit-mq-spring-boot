package dev.pgjbz.subscriptionrabbitmq.infra.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "spring.rabbitmq")
public record RabbitMQConfig(
    String exchange,
    String routingKey,
    String queue
) {

}
