package com.felipfad.fin_project.data.provider;

import com.felipfad.fin_project.DTOs.StockPrice.ExternalStockPriceResponse;
import com.felipfad.fin_project.DTOs.StockPrice.StockPriceDto;
import com.felipfad.fin_project.data.config.MarketWebClientProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class MarketDataProvider implements MarketDataProviderInterface {

        private final WebClient webClient;
        private final MarketWebClientProperties properties;

        public MarketDataProvider(WebClient webClient, MarketWebClientProperties properties) {
                this.webClient = webClient;
                this.properties = properties;
        }

        @Override
        public List<StockPriceDto> fetchDailyPrices() {

                List<String> symbols = List.of("AAPL", "GOOG");
                List<StockPriceDto> prices = new java.util.ArrayList<>();

                for (String symbol : symbols) {
                        try {
                                StockPriceDto dto = fetchLatestForSymbol(symbol);
                                prices.add(dto);

                                Thread.sleep(5000);
                        } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                throw new RuntimeException("Thread interrupted during sleep", e);
                        }
                }

                return prices;
        }

        private StockPriceDto fetchLatestForSymbol(String symbol) {

                ExternalStockPriceResponse response = webClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("") // baseUrl is already configured in WebClient
                                                .queryParam("function", "TIME_SERIES_DAILY")
                                                .queryParam("symbol", symbol)
                                                .queryParam("apikey", properties.getApiKey())
                                                .queryParam("outputsize", "compact")
                                                .build())
                                .retrieve()
                                .bodyToMono(ExternalStockPriceResponse.class)
                                .block(); // safe here since scheduler context

                if (response == null || response.getTimeSeries() == null) {
                        throw new RuntimeException("Invalid response from AlphaVantage for symbol: " + symbol);
                }

                Map<String, ExternalStockPriceResponse.DailyData> series = response.getTimeSeries();

                // Get latest date
                String latestDate = series.keySet()
                                .stream()
                                .max(Comparator.naturalOrder())
                                .orElseThrow();

                ExternalStockPriceResponse.DailyData daily = series.get(latestDate);
                LocalDate date = LocalDate.parse(latestDate);

                return new StockPriceDto(
                                symbol,
                                date,
                                new BigDecimal(daily.getOpen()),
                                new BigDecimal(daily.getHigh()),
                                new BigDecimal(daily.getLow()),
                                new BigDecimal(daily.getClose()),
                                Long.valueOf(daily.getVolume()));
        }
}
