package com.pgjbz.subscription.consumer.service;

import com.pgjbz.subscription.consumer.model.EventHistory;
import org.springframework.lang.NonNull;

public interface EventHistoryService {

    EventHistory save(@NonNull EventHistory eventHistory);

}
