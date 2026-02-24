package com.felipfad.fin_project.api.controller;

import com.felipfad.fin_project.api.service.TradeService;
import com.felipfad.fin_project.model.Trade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
public class TradesQueryController {

    private final TradeService tradeService;

    public TradesQueryController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    // GET /api/trades?symbol={symbol}  -> all trades (if no symbol) or by symbol
    @GetMapping
    public ResponseEntity<List<Trade>> getTrades(@RequestParam(required = false) String symbol) {
        if (symbol == null || symbol.isBlank()) {
            return ResponseEntity.ok(tradeService.getAllTrades());
        }
        return ResponseEntity.ok(tradeService.getTradesBySymbol(symbol));
    }
}
