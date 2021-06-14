package com.pgjbz.subscription.consumer.service;

import com.pgjbz.subscription.consumer.enums.StatusNotification;
import com.pgjbz.subscription.consumer.model.EventHistory;
import com.pgjbz.subscription.consumer.model.Subscription;
import com.pgjbz.subscription.context.TestContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ContextConfiguration(initializers = EventHistoryServiceIT.class)
public class EventHistoryServiceIT extends TestContext {

    @Autowired
    private EventHistoryService eventHistoryService;
    @Autowired
    private SubscriptionService subscriptionService;


    @ParameterizedTest
    @EnumSource(StatusNotification.class)
    public void testEventHistoryInsertNewEventHistoryExpectedSuccess(StatusNotification statusNotification) {
        var subscription = Subscription.builder()
                .id("id-99")
                .status(1)
                .build();
        var eventHistory = EventHistory.builder()
                .type(statusNotification)
                .subscription(subscription)
                .build();
        subscriptionService.save(subscription);
        EventHistory persistentEventHistory = eventHistoryService.save(eventHistory);
        assertNotNull(persistentEventHistory);
    }

}
