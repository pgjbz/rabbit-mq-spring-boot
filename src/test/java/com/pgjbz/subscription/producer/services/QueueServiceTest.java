package com.pgjbz.subscription.producer.services;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.pgjbz.subscription.configuration.RabbitMQConfig;
import com.pgjbz.subscription.producer.services.impl.QueueServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QueueServiceTest {

    private QueueService queueService;
    private RabbitTemplate rabbitTemplateMock;

    @BeforeAll
    void setup(){
        this.rabbitTemplateMock = Mockito.mock(RabbitTemplate.class);
        this.queueService = new QueueServiceImpl(rabbitTemplateMock);
    }

    @Test
    public void testSendMessageExpectedSuccess(){
        assertThatCode(() -> this.queueService.sendMessage("Test")).doesNotThrowAnyException();
        verify(this.rabbitTemplateMock)
                .convertAndSend(eq(RabbitMQConfig.TOPIC_EXCHANGE_NAME), eq("rabbit.messages"), eq("Test"));
    }

    @Test
    public void testSendEmptyMessageExpectedIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> this.queueService.sendMessage(""));
        verify(this.rabbitTemplateMock, never())
                .convertAndSend(eq(RabbitMQConfig.TOPIC_EXCHANGE_NAME), eq("rabbit.messages"), eq(""));
    }

}
