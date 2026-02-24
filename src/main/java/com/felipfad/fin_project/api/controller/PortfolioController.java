package com.felipfad.fin_project.api.controller;

import com.felipfad.fin_project.DTOs.Message.MessageResponse;
import com.felipfad.fin_project.DTOs.Portfolio.PortfolioRequest;
import com.felipfad.fin_project.DTOs.Portfolio.PortfolioResponse;
import com.felipfad.fin_project.api.service.PortfolioService;
import com.felipfad.fin_project.model.Portfolio;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping
    public List<PortfolioResponse> getAllPortfolios() {
        return portfolioService.getAllPortfolios();
    }

    @GetMapping("/{id}")
    public PortfolioResponse getPortfolioById(@PathVariable Long id) {
        return portfolioService.getPortfolioById(id);
    }

    @GetMapping("/client/{clientId}")
    public List<Portfolio> getPortfoliosByClient(@PathVariable Long clientId) {
        return portfolioService.getPortfoliosByClientId(clientId);
    }

    @PostMapping
    public PortfolioResponse createPortfolio(@RequestBody PortfolioRequest request) {
        return portfolioService.createPortfolio(request);
    }

    @PutMapping("/{id}")
    public PortfolioResponse updatePortfolio(@PathVariable Long id, @RequestBody PortfolioRequest request) {
        return portfolioService.updatePortfolio(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deletePortfolio(@PathVariable Long id) {
        portfolioService.deletePortfolio(id);
        return ResponseEntity.ok(new MessageResponse("Portfolio deleted successfully"));
    }
}
