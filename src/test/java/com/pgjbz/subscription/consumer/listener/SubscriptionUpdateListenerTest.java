package com.pgjbz.subscription.consumer.listener;

import com.pgjbz.subscription.api.http.data.request.SubscriptionNotificationDto;
import com.pgjbz.subscription.consumer.enums.StatusNotification;
import com.pgjbz.subscription.consumer.event.SubscriptionUpdateEvent;
import com.pgjbz.subscription.consumer.model.EventHistory;
import com.pgjbz.subscription.consumer.model.Subscription;
import com.pgjbz.subscription.consumer.service.EventHistoryService;
import com.pgjbz.subscription.consumer.service.SubscriptionService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubscriptionUpdateListenerTest {


    private SubscriptionService subscriptionService;
    private EventHistoryService eventHistoryService;
    private SubscriptionUpdateListener updateListener;

    @BeforeAll
    void setup() {
        this.subscriptionService = mock(SubscriptionService.class);
        this.eventHistoryService = mock(EventHistoryService.class);
        this.updateListener = new SubscriptionUpdateListener(subscriptionService, eventHistoryService);
    }

    @ParameterizedTest
    @MethodSource(value = "subscriptionUpdateArgumentesProvider")
    public void testSubscriptionEventFoundUpdateSubscriptionExpectedSuccess(StatusNotification statusNotification, String subscriptionId) {
        var subscription = Subscription.builder()
                .id(subscriptionId)
                .status(1)
                .updated(new Date())
                .created(new Date())
                .build();

        var subscriptionNotificationDto = SubscriptionNotificationDto.builder()
                .statusNotificationType(statusNotification)
                .subscriptionId(subscriptionId)
                .build();

        when(subscriptionService.findById(subscriptionId)).thenReturn(subscription);
        when(subscriptionService.save(subscription)).thenReturn(subscription);
        when(eventHistoryService.save(any(EventHistory.class))).thenReturn(new EventHistory());

        var subscriptionUpdateEvent = new SubscriptionUpdateEvent(this, subscriptionNotificationDto);

        updateListener.onApplicationEvent(subscriptionUpdateEvent);

        verify(subscriptionService).findById(subscriptionId);
        verify(subscriptionService).save(subscription);
        verify(eventHistoryService, atLeastOnce()).save(any(EventHistory.class));
    }

    @ParameterizedTest
    @MethodSource(value = "subscriptionCreateArgumentesProvider")
    public void testSubscriptionEventFoundCreateSubscriptionExpectedSuccess(StatusNotification statusNotification, String subscriptionId) {
        var subscription = Subscription.builder()
                .id(subscriptionId)
                .status(1)
                .updated(new Date())
                .created(new Date())
                .build();

        var subscriptionNotificationDto = SubscriptionNotificationDto.builder()
                .statusNotificationType(statusNotification)
                .subscriptionId(subscriptionId)
                .build();

        when(subscriptionService.findById(subscriptionId)).thenThrow(NoResultException.class);
        when(subscriptionService.save(subscription)).thenReturn(subscription);
        when(eventHistoryService.save(any(EventHistory.class))).thenReturn(new EventHistory());

        var subscriptionUpdateEvent = new SubscriptionUpdateEvent(this, subscriptionNotificationDto);

        updateListener.onApplicationEvent(subscriptionUpdateEvent);

        verify(subscriptionService).findById(subscriptionId);
        verify(subscriptionService).save(subscription);
        verify(eventHistoryService, atLeastOnce()).save(any(EventHistory.class));
    }

    private Stream<Arguments> subscriptionUpdateArgumentesProvider () {
        return Stream.of(
                Arguments.of(StatusNotification.SUBSCRIPTION_PURCHASED, "id-1"),
                Arguments.of(StatusNotification.SUBSCRIPTION_CANCELED, "id-2"),
                Arguments.of(StatusNotification.SUBSCRIPTION_RESTARTED, "id-3")
        );
    }

    private Stream<Arguments> subscriptionCreateArgumentesProvider () {
        return Stream.of(
                Arguments.of(StatusNotification.SUBSCRIPTION_PURCHASED, "id-4"),
                Arguments.of(StatusNotification.SUBSCRIPTION_CANCELED, "id-5"),
                Arguments.of(StatusNotification.SUBSCRIPTION_RESTARTED, "id-6")
        );
    }

    @Test
    public void testSubscriptionEventNullExpectedNullPointerException () {
        assertThrows(NullPointerException.class, () -> updateListener.onApplicationEvent(null));
    }

    @ParameterizedTest
    @EnumSource(StatusNotification.class)
    public void testSubscriptionEventWithoutSubscriptionIdExpectedIllegalArgumentException (StatusNotification statusNotification) {
        var subscriptionNotificationDto = SubscriptionNotificationDto.builder()
                .statusNotificationType(statusNotification)
                .build();

        var subscriptionUpdateEvent = new SubscriptionUpdateEvent(this, subscriptionNotificationDto);


        assertThrows(IllegalArgumentException.class, () -> updateListener.onApplicationEvent(subscriptionUpdateEvent));
    }

    @Test
    public void testSubscriptionEventWithoutNotificationExpectedIllegalArgumentException () {
        var subscriptionNotificationDto = SubscriptionNotificationDto.builder()
                .subscriptionId("id-1")
                .build();

        var subscriptionUpdateEvent = new SubscriptionUpdateEvent(this, subscriptionNotificationDto);

        assertThrows(IllegalArgumentException.class, () -> updateListener.onApplicationEvent(subscriptionUpdateEvent));
    }

}
