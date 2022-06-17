package dev.pgjbz.subscriptionrabbitmq.domain.models;

import java.time.LocalDateTime;

import dev.pgjbz.subscriptionrabbitmq.domain.enums.Status;

public record Subscription(
    String id,
    Status status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    
}
