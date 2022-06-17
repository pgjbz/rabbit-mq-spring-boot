package dev.pgjbz.subscriptionrabbitmq.infra.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAlias;

import dev.pgjbz.subscriptionrabbitmq.infra.enums.NotificationType;

public record NotificationDTO(
    @NotBlank(message = "subscription is mandatory")
    @JsonAlias(value = "subscription")
    String subscriptionId,
    @NotNull(message = "subscription is mandatory")
    @JsonAlias(value = "notification_type")
    NotificationType notificationType
) {
    
}
