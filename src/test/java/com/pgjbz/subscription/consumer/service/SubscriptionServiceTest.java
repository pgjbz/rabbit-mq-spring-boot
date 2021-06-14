package com.pgjbz.subscription.consumer.service;

import com.pgjbz.subscription.consumer.model.Subscription;
import com.pgjbz.subscription.consumer.repository.SubscriptionRepository;
import com.pgjbz.subscription.consumer.service.impl.SubscriptionServiceImpl;
import org.junit.jupiter.api.*;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubscriptionServiceTest {

    private SubscriptionService subscriptionService;
    private SubscriptionRepository subscriptionRepository;

    @BeforeAll
    void setup(){
       this.subscriptionRepository = mock(SubscriptionRepository.class);
       this.subscriptionService = new SubscriptionServiceImpl(subscriptionRepository);
    }

    @Test
    public void testFindByIdExpectedSuccess(){
        String id = "5793cf6b3fd833521db8c420955e6f02";
        var subscription = Subscription.builder()
                .id(id)
                .status(1)
                .created(new Date())
                .updated(new Date())
                .build();

        when(subscriptionRepository.findById(id)).thenReturn(Optional.ofNullable(subscription));

        var subscriptionReturn = subscriptionService.findById(id);
        assertNotNull(subscriptionReturn);

        verify(subscriptionRepository).findById(id);

    }

    @Test
    public void testFindByIdPassEmptyIdExpectedIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> subscriptionService.findById(null));
    }

    @Test
    public void testFinByIdWithNonExistentIdExpectedNoResultException(){
        String id = "5793cf6b3fd833521db8c420955e6f01";
        doReturn(Optional.ofNullable(null)).when(subscriptionRepository).findById(id);
        assertThrows(NoResultException.class, () -> subscriptionService.findById(id));
        verify(subscriptionRepository).findById(id);
    }

    @Test
    public void testSaveSubscriptionExpectedSuccess() {
        String id = "5793cf6b3fd833521db8c420955e6f03";
        var subscription = Subscription.builder()
                .id(id)
                .status(1)
                .created(new Date())
                .updated(new Date())
                .build();

        when(subscriptionRepository.save(subscription)).thenReturn(subscription);
        subscription = subscriptionService.save(subscription);

        assertNotNull(subscription);
        verify(subscriptionRepository).save(subscription);
    }

    @Test
    public void testSaveSubscriptionExpectedNullPointerException() {
        Subscription subscription = null;
        assertThrows(NullPointerException.class, () ->  subscriptionService.save(subscription));
    }
}
