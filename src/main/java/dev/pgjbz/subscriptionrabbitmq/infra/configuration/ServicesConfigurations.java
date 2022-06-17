package dev.pgjbz.subscriptionrabbitmq.infra.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.pgjbz.subscriptionrabbitmq.domain.adapter.services.SubscriptionServiceImpl;
import dev.pgjbz.subscriptionrabbitmq.domain.ports.services.SubscriptionService;
import dev.pgjbz.subscriptionrabbitmq.infra.repository.impl.SubscriptionRepositoryImpl;

@Configuration
public class ServicesConfigurations {

    @Bean
    public SubscriptionService subscriptionService(final SubscriptionRepositoryImpl subscriptionRepositoryImpl) {
        return new SubscriptionServiceImpl(subscriptionRepositoryImpl);
    }
}
