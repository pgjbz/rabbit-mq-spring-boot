package com.pgjbz.subscription.consumer.repository;


import com.pgjbz.subscription.consumer.model.EventHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventHistoryRepository extends JpaRepository<EventHistory, Long> {
}
