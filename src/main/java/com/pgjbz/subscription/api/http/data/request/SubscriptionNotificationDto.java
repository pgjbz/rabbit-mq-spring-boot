package com.pgjbz.subscription.api.http.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import com.pgjbz.subscription.consumer.enums.StatusNotification;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@ToString
@Builder
@Schema(name = "Subscription notification request")
public class SubscriptionNotificationDto implements Serializable {

    @JsonProperty("notification_type")
    @NotNull(message = "Notification type cannot be null")
    private StatusNotification statusNotificationType;
    @JsonProperty("subscription")
    @NotBlank(message = "Subscription cannot be empty or null")
    @Schema(name = "subscription", defaultValue = "subscription-id")
    private String subscriptionId;

    @Deprecated
    public SubscriptionNotificationDto() {
    }

    public SubscriptionNotificationDto(StatusNotification statusNotificationType, String subscriptionId) {
        this.statusNotificationType = statusNotificationType;
        this.subscriptionId = subscriptionId;
    }
}
