package com.busreserve.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI busReservationOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Bus Reservation System API")
                        .description("API Documentation for the Bus Reservation System Backend")
                        .version("v1.0.0"));
    }
}
