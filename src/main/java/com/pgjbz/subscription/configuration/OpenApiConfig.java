package com.pgjbz.subscription.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI springShopOpenAPI(@Value("${info.app.version}") String version) {
        return new OpenAPI()
                .info(new Info().title("Subscription - API")
                        .description("Desafio atualização de assinaturas")
                        .version(version));
    }

}
