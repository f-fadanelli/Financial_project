package com.felipfad.fin_project.api.controller;

import org.springframework.web.bind.annotation.*;

import com.felipfad.fin_project.api.service.StockPriceService;
import com.felipfad.fin_project.model.StockPrice;

import java.util.List;
@RestController
@RequestMapping("/api/stockPrices")
public class StockPriceController {
    private final StockPriceService stockPriceService;

    public StockPriceController(StockPriceService stockPriceService) {
        this.stockPriceService = stockPriceService;
    }

    @GetMapping
    public List<StockPrice> getStockPrices(
            @RequestParam(required = false) String symbol,
            @RequestParam(required = false) String priceDate // as String, will parse later
    ) {
        return stockPriceService.getStockPrices(symbol, priceDate);
    }

}
