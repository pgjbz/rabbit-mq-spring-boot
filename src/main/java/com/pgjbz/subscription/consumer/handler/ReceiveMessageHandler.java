package com.pgjbz.subscription.consumer.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgjbz.subscription.api.http.data.request.SubscriptionNotificationDto;
import com.pgjbz.subscription.consumer.event.SubscriptionUpdateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Log4j2
@RequiredArgsConstructor
public class ReceiveMessageHandler {

    private final ApplicationEventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    public void receiveMessage(String message) throws JsonProcessingException {
        log.info("Handle message: {}", message);
        if(!StringUtils.hasLength(message)) {
            log.warn("Empty message handled, do nothing");
            return;
        }
        publishNotificationEvent(message);
    }

    private void publishNotificationEvent(String message) throws JsonProcessingException {
        try {
            var subscriptionMessage = objectMapper.readValue(message, SubscriptionNotificationDto.class);
            eventPublisher.publishEvent(new SubscriptionUpdateEvent(this, subscriptionMessage));
        } catch (JsonProcessingException e){
            throw e;
        }
    }
}

