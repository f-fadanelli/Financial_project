package com.felipfad.fin_project.api.controller;

import com.felipfad.fin_project.api.service.TradeService;
import com.felipfad.fin_project.model.RealizedProfit;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/portfolios/{portfolioId}/realized-profits")
public class RealizedProfitController {

    private final TradeService tradeService;

    public RealizedProfitController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping
    public ResponseEntity<List<RealizedProfit>> getRealized(@PathVariable Long portfolioId,
                                                            @RequestParam(required = false) String symbol) {
        return ResponseEntity.ok(tradeService.getRealizedByPortfolio(portfolioId, symbol));
    }
}
