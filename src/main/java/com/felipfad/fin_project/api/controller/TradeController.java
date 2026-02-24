package com.felipfad.fin_project.api.controller;

import com.felipfad.fin_project.DTOs.Trade.TradeRequest;
import com.felipfad.fin_project.api.service.TradeService;
import com.felipfad.fin_project.model.Trade;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolios/{portfolioId}/trades")
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping("/buy")
    public ResponseEntity<Trade> buy(@PathVariable Long portfolioId, @RequestBody TradeRequest request) {
        Trade trade = tradeService.buy(portfolioId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(trade);
    }

    @PostMapping("/sell")
    public ResponseEntity<Trade> sell(@PathVariable Long portfolioId, @RequestBody TradeRequest request) {
        Trade trade = tradeService.sell(portfolioId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(trade);
    }
}
