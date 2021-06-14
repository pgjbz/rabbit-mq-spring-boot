package com.pgjbz.subscription.consumer.service.impl;

import com.pgjbz.subscription.consumer.model.Subscription;
import com.pgjbz.subscription.consumer.repository.SubscriptionRepository;
import com.pgjbz.subscription.consumer.service.SubscriptionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.NoResultException;
import java.util.Objects;

@Service
@Log4j2
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription findById(String id) {
        if(!StringUtils.hasLength(id)) throw new IllegalArgumentException("Id cannot be empty or null");
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new NoResultException(String.format("Subscription with id %s not found", id)));
    }

    @Override
    public Subscription save(@NonNull Subscription subscription) {
        Objects.requireNonNull(subscription);
        log.info("Saving subscription {}", subscription.toString());
        return subscriptionRepository.save(subscription);
    }
}
