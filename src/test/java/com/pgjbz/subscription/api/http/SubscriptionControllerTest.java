package com.pgjbz.subscription.api.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgjbz.subscription.api.http.data.request.SubscriptionNotificationDto;
import com.pgjbz.subscription.consumer.enums.StatusNotification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(SubscriptionController.class)
public class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApplicationEventPublisher eventPublisher;

    @ParameterizedTest
    @EnumSource(StatusNotification.class)
    public void sendRequestExpectedOkStatus(StatusNotification statusNotification) throws Exception {
        var subscriptionNotification = SubscriptionNotificationDto.builder()
                .statusNotificationType(statusNotification)
                .subscriptionId("asjhashjasbsa451")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(subscriptionNotification);
        doNothing().when(eventPublisher).publishEvent(any());
        mockMvc.perform(post("/subscriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void sendRequestExpectedBadRequestStatus() throws Exception {
        var subscriptionNotification = SubscriptionNotificationDto.builder()
                .subscriptionId("asjhashjasbsa451")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(subscriptionNotification);
        doNothing().when(eventPublisher).publishEvent(any());
        mockMvc.perform(post("/subscriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void sendRequestWithJsonErrorExpectedBadRequestStatus() throws Exception {
        doNothing().when(eventPublisher).publishEvent(any());
        mockMvc.perform(post("/subscriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{/"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void sendRequestWithInvalidNotificationTypeErrorExpectedBadRequestStatus() throws Exception {
        doNothing().when(eventPublisher).publishEvent(any());
        mockMvc.perform(post("/subscriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"notification_type\" : \"SUBSCRIPTION_PUARCHASED\", \"subscription\": \"5793cf6b3fd833521db8c420955e6f0\"'}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
