package com.pgjbz.subscription.producer.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgjbz.subscription.api.http.data.request.SubscriptionNotificationDto;
import com.pgjbz.subscription.consumer.enums.StatusNotification;
import com.pgjbz.subscription.exception.SubscriptionMessageException;
import com.pgjbz.subscription.producer.event.SubscriptionMessageEvent;
import com.pgjbz.subscription.producer.services.QueueService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubscriptionMessageListenerTest {

    private QueueService queueService;
    private ObjectMapper objectMapper;
    private SubscriptionMessageListener listener;

    @BeforeAll
    void setup(){
        this.queueService = Mockito.mock(QueueService.class);
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        this.listener = new SubscriptionMessageListener(objectMapper, queueService);
    }

    @ParameterizedTest
    @EnumSource(StatusNotification.class)
    public void testSubscriptionMessageListenExpectedSuccess(StatusNotification statusNotification) throws JsonProcessingException {
        var subscriptionNotificationDto =  SubscriptionNotificationDto.builder()
                .statusNotificationType(statusNotification)
                .subscriptionId("abc1144100148qecq")
                .build();
        var subscriptionMessageEvent =  new SubscriptionMessageEvent(this, subscriptionNotificationDto);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(subscriptionNotificationDto);
        when(objectMapper.writeValueAsString(any())).thenReturn(json);

        listener.onApplicationEvent(subscriptionMessageEvent);

        verify(objectMapper).writeValueAsString(subscriptionNotificationDto);
        verify(queueService).sendMessage(json);
    }

    @Test
    public void testSubscriptionMessageListenExpectedNullPointerException() {
        SubscriptionMessageEvent event = null;
        assertThrows(NullPointerException.class,
                () -> listener.onApplicationEvent(event));
    }

    @Test
    public void testSubscriptionMessageListenExpectedSubscriptionMessageException() throws JsonProcessingException {
        var subscriptionNotificationDto = SubscriptionNotificationDto.builder()
                .statusNotificationType(StatusNotification.SUBSCRIPTION_PURCHASED)
                .subscriptionId("abc1144100148qecq")
                .build();
        var subscriptionMessageEvent =  new SubscriptionMessageEvent(this, subscriptionNotificationDto);

        when(objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        assertThrows(SubscriptionMessageException.class, () -> listener.onApplicationEvent(subscriptionMessageEvent));
    }

}
