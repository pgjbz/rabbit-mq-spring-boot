package dev.pgjbz.subscriptionrabbitmq.infra.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import dev.pgjbz.subscriptionrabbitmq.domain.models.Subscription;
import dev.pgjbz.subscriptionrabbitmq.infra.utils.StatusUtils;

@Table(value = "subscription")
public record SubscriptionInfraUpdate(
        @Id String id,
        Integer statusId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
    public Subscription toDomainModel() {
        return new Subscription(id, StatusUtils.intToStatus(statusId), createdAt, updatedAt);
    }
}
