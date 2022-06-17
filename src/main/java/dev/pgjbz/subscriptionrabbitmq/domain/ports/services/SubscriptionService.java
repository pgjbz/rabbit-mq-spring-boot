package dev.pgjbz.subscriptionrabbitmq.domain.ports.services;

import dev.pgjbz.subscriptionrabbitmq.domain.models.Subscription;

public interface SubscriptionService {
    Subscription createSubscription(Subscription subscription);
    void updateSubscription(Subscription subscription);
}
