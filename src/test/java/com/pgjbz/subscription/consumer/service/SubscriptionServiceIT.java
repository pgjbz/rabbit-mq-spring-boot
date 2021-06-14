package com.pgjbz.subscription.consumer.service;

import com.pgjbz.subscription.consumer.model.Subscription;
import com.pgjbz.subscription.context.TestContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.NoResultException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ContextConfiguration(initializers = SubscriptionServiceIT.class)
public class SubscriptionServiceIT extends TestContext {

    @Autowired
    private SubscriptionService subscriptionService;

    @Test
    public void testSubscriptionFindByIdExpectedNoResultException() {
        assertThrows(NoResultException.class, () -> subscriptionService.findById("id-1"));
    }

    @Test
    public void testSubscriptionInsertNewSubscriptionExpectedSuccess() {
        var subscription = Subscription.builder()
                .id("id-99")
                .status(1)
                .build();

        Subscription persistentSubscription = subscriptionService.save(subscription);
        assertNotNull(persistentSubscription);
    }

    @Test
    public void testSubscriptionInsertNewSubscriptionWithoutIdExpectedJpaSystemException() {
        var subscription = Subscription.builder()
                .status(1)
                .build();

        assertThrows(JpaSystemException.class, () -> subscriptionService.save(subscription));
    }

    @Test
    public void testSubscriptionInsertNewSubscriptionWithInvalidStatusIdExpectedException() {
        var subscription = Subscription.builder()
                .id("id-99")
                .status(9999999)
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> subscriptionService.save(subscription));
    }

    @Test
    public void testSubscriptionFindByIdExpectedSuccess() {
        var subscription = Subscription.builder()
                .id("id-2")
                .status(1)
                .build();

        subscriptionService.save(subscription);

        Subscription persistentSubscription = subscriptionService.findById("id-2");
        assertNotNull(persistentSubscription);
    }
}
