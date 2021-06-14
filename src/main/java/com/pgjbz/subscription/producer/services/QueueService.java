package com.pgjbz.subscription.producer.services;

public interface QueueService {

    void sendMessage(String message);

}
