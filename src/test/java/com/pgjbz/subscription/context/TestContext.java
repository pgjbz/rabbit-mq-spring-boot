package com.pgjbz.subscription.context;

import com.pgjbz.subscription.SubscriptionProducerApplication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SubscriptionProducerApplication.class)
public abstract class TestContext implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

    public static RabbitMQContainer rabbitMQContainer = new RabbitMQContainer("rabbitmq:3-management-alpine")
            .withExposedPorts(5672, 15672)
            .withUser("guest", "guest");

    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:13")
            .withDatabaseName("desafio")
            .withUsername("rabbit")
            .withPassword("secret123");

    static {
        rabbitMQContainer.start();
        postgreSQLContainer.start();
    }

    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        TestPropertyValues.of(
                "spring.rabbitmq.host=" + rabbitMQContainer.getContainerIpAddress(),
                "spring.rabbitmq.port=" + rabbitMQContainer.getMappedPort(5672),
                "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                "spring.datasource.password=" + postgreSQLContainer.getPassword()
        ).applyTo(configurableApplicationContext);
    }

}
