package com.pgjbz.subscription.consumer.service;

import com.pgjbz.subscription.consumer.enums.StatusNotification;
import com.pgjbz.subscription.consumer.model.EventHistory;
import com.pgjbz.subscription.consumer.model.Subscription;
import com.pgjbz.subscription.consumer.repository.EventHistoryRepository;
import com.pgjbz.subscription.consumer.service.impl.EventHistoryServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventHistoryServiceTest {

    private EventHistoryService eventHistoryService;
    private EventHistoryRepository eventHistoryRepository;

    @BeforeAll
    void setup(){
        this.eventHistoryRepository = mock(EventHistoryRepository.class);
        this.eventHistoryService = new EventHistoryServiceImpl(eventHistoryRepository);
    }


    @ParameterizedTest
    @MethodSource(value = "providerArgumentEventHistory")
    public void testSaveEventHistoryExpectedSuccess(long id, String notification) {

        var subscription = Subscription.builder()
                .status(1)
                .created(new Date())
                .updated(new Date())
                .build();

        var eventHistory = EventHistory.builder()
                .id(id)
                .subscription(subscription)
                .created(new Date())
                .type(StatusNotification.valueOf(notification))
                .build();

        when(eventHistoryRepository.save(eventHistory)).thenReturn(eventHistory);
        eventHistory = eventHistoryService.save(eventHistory);

        assertNotNull(eventHistory);
        verify(eventHistoryRepository).save(eventHistory);
    }

    private static Stream<Arguments> providerArgumentEventHistory() {
        return Stream.of(
                Arguments.of(1L, StatusNotification.SUBSCRIPTION_PURCHASED.name()),
                Arguments.of(2L, StatusNotification.SUBSCRIPTION_CANCELED.name()),
                Arguments.of(3L, StatusNotification.SUBSCRIPTION_RESTARTED.name())
        );
    }

    @Test
    public void testSaveEventHistoryExpectedNullPointerException() {
        String id = "5793cf6b3fd833521db8c420955e6f04";
        EventHistory eventHistory = null;

        assertThrows(NullPointerException.class, () ->  eventHistoryService.save(eventHistory));

    }
}
