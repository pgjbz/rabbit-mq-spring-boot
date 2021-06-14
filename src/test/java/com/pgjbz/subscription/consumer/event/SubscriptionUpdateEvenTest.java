package com.pgjbz.subscription.consumer.event;

import com.pgjbz.subscription.api.http.data.request.SubscriptionNotificationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertThrows;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubscriptionUpdateEvenTest {

    @Test
    public void testInstantiateSubscriptionUpdateEventExpectedSuccess() {
        var subscriptionUpdate = new SubscriptionUpdateEvent(this, new SubscriptionNotificationDto());
        Assertions.assertNotNull(subscriptionUpdate);
    }

    @Test
    public void testTryInstantiateNewSubscriptionUpdateEventExpectedNullPointerException() {
        SubscriptionNotificationDto subscriptionUpdate = null;
        assertThrows(NullPointerException.class,
                () -> new SubscriptionUpdateEvent(this, subscriptionUpdate));
    }
    
}
