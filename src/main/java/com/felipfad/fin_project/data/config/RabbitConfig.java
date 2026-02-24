package com.felipfad.fin_project.data.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE_NAME = "stock_prices_exchange";
    public static final String QUEUE_NAME = "portfolio_snapshot_queue";

    @Bean
    public FanoutExchange stockPricesExchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue portfolioSnapshotQueue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    @Bean
    public Binding binding(Queue portfolioSnapshotQueue, FanoutExchange stockPricesExchange) {
        return BindingBuilder.bind(portfolioSnapshotQueue).to(stockPricesExchange);
    }
}
