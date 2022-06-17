package dev.pgjbz.subscriptionrabbitmq.infra.http;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.pgjbz.subscriptionrabbitmq.domain.models.Subscription;
import dev.pgjbz.subscriptionrabbitmq.infra.dto.NotificationDTO;
import dev.pgjbz.subscriptionrabbitmq.infra.services.QueueService;

@RestController
@RequestMapping(value = "/v1/subscription")
public class SubscriptionController {

    private final QueueService queueService;
    private final ObjectMapper objectMapper;

    public SubscriptionController(QueueService queueService,
            ObjectMapper objectMapper) {
        this.queueService = queueService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void postMethodName(@RequestBody @Valid NotificationDTO entity) {
        var now = LocalDateTime.now();
        try {
            queueService.publishMessage(objectMapper.writeValueAsString(
                    new Subscription(entity.subscriptionId(), entity.notificationType().toStatus(), now, now)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
