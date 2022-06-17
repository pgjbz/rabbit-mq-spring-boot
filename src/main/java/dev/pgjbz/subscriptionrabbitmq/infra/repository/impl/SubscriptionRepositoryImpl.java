package dev.pgjbz.subscriptionrabbitmq.infra.repository.impl;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import dev.pgjbz.subscriptionrabbitmq.domain.models.Subscription;
import dev.pgjbz.subscriptionrabbitmq.domain.ports.repository.SubscriptionRepository;
import dev.pgjbz.subscriptionrabbitmq.infra.models.SubscriptionInfraInsert;
import dev.pgjbz.subscriptionrabbitmq.infra.models.SubscriptionInfraUpdate;
import dev.pgjbz.subscriptionrabbitmq.infra.repository.ReactiveSubscriptionRepository;
import dev.pgjbz.subscriptionrabbitmq.infra.repository.ReactiveSubscriptionRepositoryInsert;
import dev.pgjbz.subscriptionrabbitmq.infra.utils.StatusUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Repository
public class SubscriptionRepositoryImpl
        implements SubscriptionRepository {

    private final Logger log = LoggerFactory.getLogger(SubscriptionRepositoryImpl.class);

    private final ReactiveSubscriptionRepository subscriptionRepository;
    private final ReactiveSubscriptionRepositoryInsert subscriptionRepositoryInsert;

    public SubscriptionRepositoryImpl(ReactiveSubscriptionRepository subscriptionRepository,
            ReactiveSubscriptionRepositoryInsert subscriptionRepositoryInsert) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionRepositoryInsert = subscriptionRepositoryInsert;
    }

    @Override
    public Subscription createSubscription(Subscription subscription) {
        LocalDateTime now = LocalDateTime.now();
        try {
            subscriptionRepositoryInsert
                    .save(new SubscriptionInfraInsert(subscription.id(), switch (subscription.status()) {
                        case ACTIVED -> 1;
                        case DESATIVED -> 2;
                        case RESTARTED -> 3;
                    }, now, now))
                    .subscribeOn(Schedulers.boundedElastic()).log().block();
        } catch (Exception e) {
            updateSubscription(subscription);
        }
        return subscription;
    }

    @Override
    public void updateSubscription(Subscription subscription) {
        LocalDateTime now = LocalDateTime.now();
        log.info("subscription: {}", subscription);
        var status = StatusUtils.statusToInteger(subscription.status());

        subscriptionRepository
                .findById(subscription.id())
                .flatMap(sub -> {
                    var subStatus = StatusUtils.intToStatus(sub.statusId());
                    if (!Objects.equals(subStatus, subscription.status())) {
                        var subUpdate = new SubscriptionInfraUpdate(subscription.id(), status, subscription.createdAt(),
                                now);
                        log.info("update subscription: {}", sub);
                        return subscriptionRepository
                                .save(subUpdate)
                                .subscribeOn(Schedulers.boundedElastic());
                    } else {

                        log.info("do nothing");
                    }
                    return Mono.empty();
                })
                .block();

    }

    @Override
    public Optional<Subscription> findById(String subscriptionId) {
        return subscriptionRepository.findById(subscriptionId).subscribeOn(Schedulers.boundedElastic())
                .map(SubscriptionInfraUpdate::toDomainModel).blockOptional();
    }

}
