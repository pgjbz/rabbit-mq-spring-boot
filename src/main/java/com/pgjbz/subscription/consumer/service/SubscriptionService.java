package com.pgjbz.subscription.consumer.service;

import com.pgjbz.subscription.consumer.model.Subscription;
import org.springframework.lang.NonNull;

public interface SubscriptionService {

    Subscription findById(String id);
    Subscription save(@NonNull Subscription subscription);

}
