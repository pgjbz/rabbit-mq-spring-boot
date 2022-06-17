package dev.pgjbz.subscriptionrabbitmq.infra.enums;

import dev.pgjbz.subscriptionrabbitmq.domain.enums.Status;

public enum NotificationType {
    SUBSCRIPTION_PURCHASED {
        @Override
        public Status toStatus() {
            return Status.ACTIVED;
        }
    },
    SUBSCRIPTION_CANCELED {
        @Override
        public Status toStatus() {
            return Status.DESATIVED;
        }
    },
    SUBSCRIPTION_RESTARTED {
        @Override
        public Status toStatus() {
            return Status.RESTARTED;
        }
    };

    public abstract Status toStatus();
}