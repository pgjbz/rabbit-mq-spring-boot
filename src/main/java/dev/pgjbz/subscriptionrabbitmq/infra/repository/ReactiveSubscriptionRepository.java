package dev.pgjbz.subscriptionrabbitmq.infra.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import dev.pgjbz.subscriptionrabbitmq.infra.models.SubscriptionInfraUpdate;

public interface ReactiveSubscriptionRepository extends ReactiveCrudRepository<SubscriptionInfraUpdate, String> {
    
}
