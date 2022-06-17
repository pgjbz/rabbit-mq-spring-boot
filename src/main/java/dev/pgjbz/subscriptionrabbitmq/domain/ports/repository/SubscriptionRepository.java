package dev.pgjbz.subscriptionrabbitmq.domain.ports.repository;

import java.util.Optional;

import dev.pgjbz.subscriptionrabbitmq.domain.models.Subscription;

public interface SubscriptionRepository {
    Subscription createSubscription(Subscription subscription);
    void updateSubscription(Subscription subscription);
    Optional<Subscription> findById(String subscriptionId);
}
