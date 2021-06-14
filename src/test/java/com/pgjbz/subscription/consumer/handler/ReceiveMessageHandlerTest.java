package com.pgjbz.subscription.consumer.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgjbz.subscription.api.http.data.request.SubscriptionNotificationDto;
import com.pgjbz.subscription.consumer.event.SubscriptionUpdateEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReceiveMessageHandlerTest {

    private ApplicationEventPublisher eventPublisher;
    private ObjectMapper objectMapper;
    private ReceiveMessageHandler receiveMessageHandler;

    @BeforeAll
    void setup() {
        this.eventPublisher = mock(ApplicationEventPublisher.class);
        this.objectMapper = mock(ObjectMapper.class);
        this.receiveMessageHandler = new ReceiveMessageHandler(eventPublisher, objectMapper);
    }

    @Test
    public void receiveMessageExpectedSuccess() throws Exception{
        String json = "{\"notification_type\" : \"SUBSCRIPTION_PURCHASED\", \"subscription\": \"id-1\"}";
        var subscriptionNotification = SubscriptionNotificationDto.builder()
                .subscriptionId("id-1")
                .build();
        doReturn(subscriptionNotification).when(objectMapper).readValue(json, SubscriptionNotificationDto.class);
        doNothing().when(eventPublisher).publishEvent(any(SubscriptionUpdateEvent.class));

        receiveMessageHandler.receiveMessage(json);

        verify(eventPublisher).publishEvent(any(SubscriptionUpdateEvent.class));
        verify(objectMapper).readValue(json, SubscriptionNotificationDto.class);
    }

    @Test
    public void testReceiveEmptyMessageExpectedDoNothing() throws Exception {
        String json = "";
        var subscriptionNotification = SubscriptionNotificationDto.builder()
                .subscriptionId("id-1")
                .build();
        doReturn(subscriptionNotification).when(objectMapper).readValue(json, SubscriptionNotificationDto.class);
        receiveMessageHandler.receiveMessage(json);
        verify(objectMapper, never()).readValue(json, SubscriptionNotificationDto.class);
    }

    @Test
    public void testReceiveInvalidMessageExpectedJsonProcessingException() throws Exception {
        String json = "{\"notification_type\" : \"SUBSCRIPTION_PURCHAESED\", \"subscription\": \"id-2\"}";
        doThrow(JsonProcessingException.class).when(objectMapper).readValue(json, SubscriptionNotificationDto.class);
        assertThrows(JsonProcessingException.class, () -> receiveMessageHandler.receiveMessage(json));
        verify(objectMapper).readValue(json, SubscriptionNotificationDto.class);
    }

}
