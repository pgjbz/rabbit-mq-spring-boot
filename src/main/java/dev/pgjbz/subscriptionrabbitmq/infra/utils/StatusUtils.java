package dev.pgjbz.subscriptionrabbitmq.infra.utils;

import dev.pgjbz.subscriptionrabbitmq.domain.enums.Status;

public class StatusUtils {

    private StatusUtils() {}

    public static int statusToInteger(Status status) {
        return switch (status) {
            case ACTIVED -> 1;
            case DESATIVED -> 2;
            case RESTARTED -> 3;
        };
    }

    public static Status intToStatus(Integer status) {
        return switch (status) {
            case 1 -> Status.ACTIVED;
            case 2 -> Status.DESATIVED;
            default -> Status.RESTARTED;
        };
    }

}
