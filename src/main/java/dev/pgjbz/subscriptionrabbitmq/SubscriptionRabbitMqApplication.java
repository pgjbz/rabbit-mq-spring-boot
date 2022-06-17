package dev.pgjbz.subscriptionrabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import dev.pgjbz.subscriptionrabbitmq.infra.configuration.RabbitMQConfig;

@SpringBootApplication
@EnableR2dbcRepositories
@EnableConfigurationProperties(RabbitMQConfig.class)
public class SubscriptionRabbitMqApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubscriptionRabbitMqApplication.class, args);
	}

}
