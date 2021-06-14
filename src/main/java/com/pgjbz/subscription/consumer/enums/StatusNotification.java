package com.pgjbz.subscription.consumer.enums;

import lombok.Getter;

public enum StatusNotification {

    SUBSCRIPTION_PURCHASED(1),
    SUBSCRIPTION_RESTARTED(1),
    SUBSCRIPTION_CANCELED(2);

    @Getter
    private final Integer status;

    StatusNotification(Integer status) {
        this.status = status;
    }


}
