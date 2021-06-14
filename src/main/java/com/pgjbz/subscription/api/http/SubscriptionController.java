package com.pgjbz.subscription.api.http;

import com.pgjbz.subscription.producer.event.SubscriptionMessageEvent;
import com.pgjbz.subscription.api.http.data.request.SubscriptionNotificationDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "subscriptions")
@RequiredArgsConstructor
@Log4j2
@Tag(name = "Subscriptions")
public class SubscriptionController {

    private final ApplicationEventPublisher eventPublisher;

    @PostMapping
    public ResponseEntity<Void> update(@Valid @RequestBody SubscriptionNotificationDto subscriptionNotification) {
        var httpStatus = HttpStatus.OK;
        String subscriptionId = subscriptionNotification.getSubscriptionId();

        log.info("Emitting event for subscription {}", subscriptionId);
        eventPublisher.publishEvent(new SubscriptionMessageEvent(this, subscriptionNotification));
        return ResponseEntity
                .status(httpStatus)
                .build();

    }

}
