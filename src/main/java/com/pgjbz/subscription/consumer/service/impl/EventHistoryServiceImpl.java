package com.pgjbz.subscription.consumer.service.impl;

import com.pgjbz.subscription.consumer.model.EventHistory;
import com.pgjbz.subscription.consumer.repository.EventHistoryRepository;
import com.pgjbz.subscription.consumer.service.EventHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Log4j2
@RequiredArgsConstructor
public class EventHistoryServiceImpl implements EventHistoryService {

    private final EventHistoryRepository eventHistoryRepository;

    @Override
    public EventHistory save(EventHistory eventHistory) {
        Objects.requireNonNull(eventHistory);
        log.info("Saving event history {}", eventHistory.toString());
        return eventHistoryRepository.save(eventHistory);
    }
}
