package com.pgjbz.subscription.producer.event;

import com.pgjbz.subscription.api.http.data.request.SubscriptionNotificationDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.NonNull;

import java.util.Objects;


public class SubscriptionMessageEvent  extends ApplicationEvent {

    @Getter
    private final SubscriptionNotificationDto subscriptionResponse;

    public SubscriptionMessageEvent(Object source, @NonNull SubscriptionNotificationDto subscriptionResponse) {
        super(source);
        this.subscriptionResponse = Objects.requireNonNull(subscriptionResponse);
    }

}
