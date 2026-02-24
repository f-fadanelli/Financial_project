package com.felipfad.fin_project.api.service;

import com.felipfad.fin_project.model.StockPrice;
import com.felipfad.fin_project.repository.StockPriceRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockPriceService {
    
    private final StockPriceRepository stockPriceRepository;

    public StockPriceService(StockPriceRepository stockPriceRepository) {
        this.stockPriceRepository = stockPriceRepository;
    }

    public List<StockPrice> getStockPrices(String symbol, String priceDateStr) {
        LocalDate priceDate = null;

        if (priceDateStr != null) {
            priceDate = LocalDate.parse(priceDateStr); // yyyy-MM-dd
        }

        List<StockPrice> result = new ArrayList<>();

        if (symbol != null && priceDate != null) {
            Optional<StockPrice> sp = stockPriceRepository.findBySymbolAndPriceDate(symbol, priceDate);
            sp.ifPresent(result::add);
        } else if (symbol != null) {
            result = stockPriceRepository.findBySymbol(symbol);
        } else if (priceDate != null) {
            result = stockPriceRepository.findByPriceDate(priceDate);
        } else {
            result = stockPriceRepository.findAll();
        }
        return result;
    }
}
