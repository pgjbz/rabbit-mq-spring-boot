package com.pgjbz.subscription.producer.services;

import com.pgjbz.subscription.context.TestContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(initializers = QueueServiceIT.class)
public class QueueServiceIT extends TestContext{

    @Autowired
    private QueueService queueService;

    @Test
    public void testSendMessage() {
        queueService.sendMessage("{\"notification_type\" : \"SUBSCRIPTION_PURCHASED\", \"subscription\": \"5793cf6b3fd833521db8c420955e6f01\"}");
    }


}
