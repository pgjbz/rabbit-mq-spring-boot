package dev.pgjbz.subscriptionrabbitmq.domain.models;

import java.time.LocalDateTime;

public record EventHistory(
    String type,
    String subscriptionId,
    LocalDateTime createdAt
) {
    
}
