package com.felipfad.fin_project.data.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.felipfad.fin_project.data.provider.StockIngestionService;

@Component
public class MarketScheduler {

    private final StockIngestionService ingestionService;

    public MarketScheduler(StockIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    // Every weekday at 18:00
    @Scheduled(cron = "0 0 18 * * MON-FRI")
    public void runDailyIngestion() {
        ingestionService.ingestDailyPrices();
    }
}