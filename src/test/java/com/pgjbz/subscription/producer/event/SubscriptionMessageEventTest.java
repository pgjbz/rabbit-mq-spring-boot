package com.pgjbz.subscription.producer.event;

import com.pgjbz.subscription.api.http.data.request.SubscriptionNotificationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubscriptionMessageEventTest {

    @Test
    public void testInstantiateSubscriptionMessageEventExpectedSuccess() {
        var subscriptionMessage = new SubscriptionMessageEvent(this, new SubscriptionNotificationDto());
        Assertions.assertNotNull(subscriptionMessage);
    }

    @Test
    public void testTryInstantiateNewSubscriptionMessageEventExpectedNullPointerException() {
        SubscriptionNotificationDto subscriptionMessageEvent = null;
        assertThrows(NullPointerException.class,
                () -> new SubscriptionMessageEvent(this, subscriptionMessageEvent));
    }
    
}
