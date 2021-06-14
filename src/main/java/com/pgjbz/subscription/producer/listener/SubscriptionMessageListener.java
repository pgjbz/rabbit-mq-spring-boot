package com.pgjbz.subscription.producer.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgjbz.subscription.exception.SubscriptionMessageException;
import com.pgjbz.subscription.producer.event.SubscriptionMessageEvent;
import com.pgjbz.subscription.producer.services.QueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Log4j2
@Component
@RequiredArgsConstructor
public class SubscriptionMessageListener implements ApplicationListener<SubscriptionMessageEvent> {

    private final ObjectMapper objectMapper;
    private final QueueService queueService;

    @Override
    public void onApplicationEvent(@NonNull SubscriptionMessageEvent subscriptionMessageEvent) {
        Objects.requireNonNull(subscriptionMessageEvent);
        var subscriptionNotification = subscriptionMessageEvent.getSubscriptionResponse();
        log.info("Received event from subscription {}", subscriptionNotification.getSubscriptionId());
        sendNotificationMessageToQueue(subscriptionMessageEvent);
    }

    private void sendNotificationMessageToQueue(SubscriptionMessageEvent subscriptionMessageEvent) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(subscriptionMessageEvent.getSubscriptionResponse());
        } catch (JsonProcessingException e) {
            throw new SubscriptionMessageException(e.getMessage());
        }
        queueService.sendMessage(json);
    }
}
