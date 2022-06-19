package dev.pgjbz.subscriptionrabbitmq.domain.ports.services;

import dev.pgjbz.subscriptionrabbitmq.domain.models.Subscription;

public sealed interface SubscriptionService permits SubscriptionServiceImpl {
    Subscription createSubscription(Subscription subscription);
    void updateSubscription(Subscription subscription);
}
