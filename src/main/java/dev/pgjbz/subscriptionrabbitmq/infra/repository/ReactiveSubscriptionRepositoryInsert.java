package dev.pgjbz.subscriptionrabbitmq.infra.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import dev.pgjbz.subscriptionrabbitmq.infra.models.SubscriptionInfraInsert;

public interface ReactiveSubscriptionRepositoryInsert extends ReactiveCrudRepository<SubscriptionInfraInsert, String> {
    
}
