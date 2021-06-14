package com.pgjbz.subscription.consumer.repository;

import com.pgjbz.subscription.consumer.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
}
