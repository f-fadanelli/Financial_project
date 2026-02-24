package com.felipfad.fin_project.data.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
public class PortfolioSnapshotListener implements PortfolioSnapshotListenerInterface {

    private final PortfolioSnapshotService snapshotService;

    public PortfolioSnapshotListener(PortfolioSnapshotService snapshotService) {
        this.snapshotService = snapshotService;
    }

    @RabbitListener(queues = "portfolio_snapshot_queue")
    public void handlePriceUpdate(Map<String, String> message) {
        LocalDate priceDate = LocalDate.parse(message.get("priceDate"));
        snapshotService.createSnapshotsForDate(priceDate);
    }
}