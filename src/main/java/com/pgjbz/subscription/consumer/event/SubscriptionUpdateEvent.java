package com.pgjbz.subscription.consumer.event;

import com.pgjbz.subscription.api.http.data.request.SubscriptionNotificationDto;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.context.ApplicationEvent;

import java.util.Objects;

public class SubscriptionUpdateEvent extends ApplicationEvent {

    @Getter
    private final SubscriptionNotificationDto subscriptionNotification;

    public SubscriptionUpdateEvent(Object source, @NonNull SubscriptionNotificationDto subscriptionNotification) {
        super(source);
        this.subscriptionNotification = Objects.requireNonNull(subscriptionNotification);
    }
}
