package com.pgjbz.subscription.consumer.listener;

import com.pgjbz.subscription.api.http.data.request.SubscriptionNotificationDto;
import com.pgjbz.subscription.consumer.enums.StatusNotification;
import com.pgjbz.subscription.consumer.event.SubscriptionUpdateEvent;
import com.pgjbz.subscription.consumer.model.EventHistory;
import com.pgjbz.subscription.consumer.model.Subscription;
import com.pgjbz.subscription.consumer.service.EventHistoryService;
import com.pgjbz.subscription.consumer.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.NoResultException;
import java.util.Objects;

@Component
@Log4j2
@RequiredArgsConstructor
public class SubscriptionUpdateListener implements ApplicationListener<SubscriptionUpdateEvent> {

    private final SubscriptionService subscriptionService;
    private final EventHistoryService eventHistoryService;

    @Override
    public void onApplicationEvent(@NonNull SubscriptionUpdateEvent subscriptionUpdateEvent) {
        Objects.requireNonNull(subscriptionUpdateEvent);
        SubscriptionNotificationDto subscriptionNotification = subscriptionUpdateEvent.getSubscriptionNotification();
        log.info("Receive update event {}", subscriptionNotification.toString());
        executeSubscriptionUpdate(subscriptionNotification);
    }

    private void executeSubscriptionUpdate(SubscriptionNotificationDto subscriptionNotification) {
        var notificationType = subscriptionNotification.getStatusNotificationType();
        String subscriptionId = subscriptionNotification.getSubscriptionId();
        Subscription subscription = getSubscription(notificationType, subscriptionId);
        saveSubscriptionAndUpdateHistory(subscriptionNotification, subscription);
    }

    private void saveSubscriptionAndUpdateHistory(SubscriptionNotificationDto subscriptionNotification, Subscription subscription) {
        subscriptionService.save(subscription);
        eventHistoryService.save(new EventHistory(subscription, subscriptionNotification.getStatusNotificationType()));
    }

    private Subscription getSubscription(StatusNotification statusNotification, String subscriptionId) {
        if(!StringUtils.hasLength(subscriptionId)) throw new IllegalArgumentException("Subscription id cannot be null or empty");
        if(Objects.isNull(statusNotification)) throw new IllegalArgumentException("Notification cannot be null");
        Subscription subscription;
        try {
            subscription = subscriptionService.findById(subscriptionId);
            subscription.setStatus(statusNotification);
            log.info("Subscription {} founded updating", subscriptionId);
        } catch (NoResultException e){
            log.info(String.format("Subscription %s not found creating", subscriptionId));
            subscription = new Subscription(subscriptionId, statusNotification);
        }
        return subscription;
    }
}
