package dev.pgjbz.subscriptionrabbitmq.domain.adapter.services;

import java.time.LocalDateTime;
import java.util.Objects;

import dev.pgjbz.subscriptionrabbitmq.domain.models.Subscription;
import dev.pgjbz.subscriptionrabbitmq.domain.ports.repository.SubscriptionRepository;
import dev.pgjbz.subscriptionrabbitmq.domain.ports.services.SubscriptionService;
import dev.pgjbz.subscriptionrabbitmq.exceptions.DataNotFoundException;

public record SubscriptionServiceImpl(
        SubscriptionRepository subscriptionRepository) implements SubscriptionService {

    @Override
    public Subscription createSubscription(Subscription subscription) {
        return subscriptionRepository.createSubscription(subscription);
    }

    @Override
    public void updateSubscription(Subscription subscription) {
        final String subscriptionId = subscription.id();
        subscriptionRepository.findById(subscriptionId)
                .ifPresentOrElse(sub -> {
                    if (!Objects.equals(sub.status(), subscription.status())) {
                        var newSub = new Subscription(subscriptionId, subscription.status(), sub.createdAt(),
                                LocalDateTime.now());
                        subscriptionRepository.updateSubscription(newSub);
                    }
                }, () -> {
                    throw new DataNotFoundException(
                            String.format("subscription with id %s not found", subscriptionId));
                });
    }

}
