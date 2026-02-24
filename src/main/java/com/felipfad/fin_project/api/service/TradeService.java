package com.felipfad.fin_project.api.service;

import com.felipfad.fin_project.DTOs.Trade.TradeRequest;
import com.felipfad.fin_project.model.*;
import com.felipfad.fin_project.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

    private final PortfolioRepository portfolioRepository;
    private final StockPriceRepository stockPriceRepository;
    private final TradeRepository tradeRepository;
    private final PortfolioPositionRepository positionRepository;
    private final RealizedProfitRepository realizedProfitRepository;

    public TradeService(PortfolioRepository portfolioRepository,
                        StockPriceRepository stockPriceRepository,
                        TradeRepository tradeRepository,
                        PortfolioPositionRepository positionRepository,
                        RealizedProfitRepository realizedProfitRepository) {
        this.portfolioRepository = portfolioRepository;
        this.stockPriceRepository = stockPriceRepository;
        this.tradeRepository = tradeRepository;
        this.positionRepository = positionRepository;
        this.realizedProfitRepository = realizedProfitRepository;
    }

    private BigDecimal determinePrice(String symbol, BigDecimal providedPrice) {
        if (providedPrice != null) return providedPrice;
        return stockPriceRepository.findBySymbol(symbol)
                .stream()
                .max(Comparator.comparing(sp -> sp.getPriceDate()))
                .map(StockPrice::getClosePrice)
                .orElseThrow(() -> new RuntimeException("No price available for symbol: " + symbol));
    }

    @Transactional
    public Trade buy(Long portfolioId, TradeRequest request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));

        BigDecimal price = determinePrice(request.getSymbol(), request.getPrice());
        BigDecimal qty = request.getQuantity();
        if (qty == null || qty.compareTo(BigDecimal.ZERO) <= 0) throw new RuntimeException("Quantity must be > 0");

        Trade trade = new Trade(portfolio, request.getSymbol(), TradeType.BUY, qty, price);
        trade.setExecutedAt(LocalDateTime.now());
        trade = tradeRepository.save(trade);

        // update or create position
        PortfolioPosition position = positionRepository.findByPortfolioIdAndSymbol(portfolioId, request.getSymbol())
                .orElse(null);

        if (position == null) {
            PortfolioPosition p = new PortfolioPosition(portfolio, request.getSymbol(), qty, price);
            positionRepository.save(p);
        } else {
            BigDecimal oldQty = position.getQuantity();
            BigDecimal oldAvg = position.getAverageBuyPrice();
            BigDecimal newQty = oldQty.add(qty);
            BigDecimal newAvg = (oldAvg.multiply(oldQty).add(price.multiply(qty))).divide(newQty, RoundingMode.HALF_UP);
            position.setQuantity(newQty);
            position.setAverageBuyPrice(newAvg);
            position.setUpdatedAt(LocalDateTime.now());
            positionRepository.save(position);
        }

        return trade;
    }

    @Transactional
    public Trade sell(Long portfolioId, TradeRequest request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));

        BigDecimal price = determinePrice(request.getSymbol(), request.getPrice());
        BigDecimal qty = request.getQuantity();
        if (qty == null || qty.compareTo(BigDecimal.ZERO) <= 0) throw new RuntimeException("Quantity must be > 0");

        PortfolioPosition position = positionRepository.findByPortfolioIdAndSymbol(portfolioId, request.getSymbol())
                .orElseThrow(() -> new RuntimeException("No position for symbol to sell"));

        if (position.getQuantity().compareTo(qty) < 0) throw new RuntimeException("Not enough quantity to sell");

        Trade trade = new Trade(portfolio, request.getSymbol(), TradeType.SELL, qty, price);
        trade.setExecutedAt(LocalDateTime.now());
        trade = tradeRepository.save(trade);

        // compute realized profit
        BigDecimal avgBuy = position.getAverageBuyPrice();
        BigDecimal realized = price.subtract(avgBuy).multiply(qty);

        RealizedProfit rp = new RealizedProfit(portfolio, request.getSymbol(), trade, realized);
        realizedProfitRepository.save(rp);

        // update or remove position
        BigDecimal remaining = position.getQuantity().subtract(qty);
        if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
            positionRepository.delete(position);
        } else {
            position.setQuantity(remaining);
            position.setUpdatedAt(LocalDateTime.now());
            positionRepository.save(position);
        }

        return trade;
    }

    // -- Query methods --
    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    public List<Trade> getTradesByPortfolio(Long portfolioId, String symbol) {
        if (symbol == null || symbol.isBlank()) return tradeRepository.findByPortfolioId(portfolioId);
        return tradeRepository.findByPortfolioIdAndSymbol(portfolioId, symbol);
    }

    public List<Trade> getTradesBySymbol(String symbol) {
        return tradeRepository.findBySymbol(symbol);
    }

    public List<PortfolioPosition> getPositionsByPortfolio(Long portfolioId, String symbol) {
        if (symbol == null || symbol.isBlank()) return positionRepository.findByPortfolioId(portfolioId);
        Optional<PortfolioPosition> p = positionRepository.findByPortfolioIdAndSymbol(portfolioId, symbol);
        return p.map(List::of).orElse(List.of());
    }

    public List<RealizedProfit> getRealizedByPortfolio(Long portfolioId, String symbol) {
        if (symbol == null || symbol.isBlank()) return realizedProfitRepository.findByPortfolioId(portfolioId);
        return realizedProfitRepository.findByPortfolioIdAndSymbol(portfolioId, symbol);
    }
}
