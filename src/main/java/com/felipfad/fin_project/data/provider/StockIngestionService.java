package com.felipfad.fin_project.data.provider;

import com.felipfad.fin_project.DTOs.StockPrice.StockPriceDto;
import com.felipfad.fin_project.model.StockPrice;
import com.felipfad.fin_project.repository.StockPriceRepository;
import com.felipfad.fin_project.data.config.RabbitConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class StockIngestionService {

    private final MarketDataProviderInterface marketDataProvider;
    private final StockPriceRepository stockPriceRepository;
    private final RabbitTemplate rabbitTemplate;

    public StockIngestionService(MarketDataProviderInterface marketDataProvider,
                                StockPriceRepository stockPriceRepository,
                                RabbitTemplate rabbitTemplate) {
        this.marketDataProvider = marketDataProvider;
        this.stockPriceRepository = stockPriceRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public void ingestDailyPrices() {
        List<StockPriceDto> prices = marketDataProvider.fetchDailyPrices();
        LocalDate latestDate = null;

        for (StockPriceDto dto : prices) {
            boolean exists = stockPriceRepository.findBySymbolAndPriceDate(dto.getSymbol(), dto.getPriceDate()).isPresent();
            if (!exists) {
                StockPrice stockPrice = new StockPrice();
                stockPrice.setSymbol(dto.getSymbol());
                stockPrice.setPriceDate(dto.getPriceDate());
                stockPrice.setOpenPrice(dto.getOpenPrice());
                stockPrice.setHighPrice(dto.getHighPrice());
                stockPrice.setLowPrice(dto.getLowPrice());
                stockPrice.setClosePrice(dto.getClosePrice());
                stockPrice.setVolume(dto.getVolume());
                stockPrice.setCreatedAt(LocalDateTime.now());
                stockPriceRepository.save(stockPrice);
            }
            latestDate = dto.getPriceDate();
        }

        // Publish event to RabbitMQ
        if (latestDate != null) {
            rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                "",  // fanout ignores routing key
                Map.of("priceDate", latestDate.toString())
            );
        }
    }
}