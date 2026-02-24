package com.felipfad.fin_project.data.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class MarketWebClientConfig {

    @Bean
    public WebClient marketWebClient(MarketWebClientProperties properties) {
        return WebClient.builder().baseUrl(properties.getBaseUrl()).build();
    }
}