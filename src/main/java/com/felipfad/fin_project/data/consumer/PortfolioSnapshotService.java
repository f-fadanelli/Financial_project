package com.felipfad.fin_project.data.consumer;

import com.felipfad.fin_project.model.*;
import com.felipfad.fin_project.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PortfolioSnapshotService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioSnapshotRepository snapshotRepository;
    private final PortfolioPositionRepository positionRepository;
    private final StockPriceRepository stockPriceRepository;
    private final RealizedProfitRepository realizedProfitRepository;

    public PortfolioSnapshotService(
            PortfolioRepository portfolioRepository,
            PortfolioSnapshotRepository snapshotRepository,
            PortfolioPositionRepository positionRepository,
            StockPriceRepository stockPriceRepository,
            RealizedProfitRepository realizedProfitRepository) {

        this.portfolioRepository = portfolioRepository;
        this.snapshotRepository = snapshotRepository;
        this.positionRepository = positionRepository;
        this.stockPriceRepository = stockPriceRepository;
        this.realizedProfitRepository = realizedProfitRepository;
    }

    @Transactional
    public void createSnapshotsForDate(LocalDate priceDate) {

        var portfolios = portfolioRepository.findAll();

        for (var portfolio : portfolios) {

            // Avoid duplicate snapshot
            boolean exists = snapshotRepository
                    .findByPortfolioIdAndSnapshotDate(portfolio.getId(), priceDate)
                    .isPresent();

            if (exists) continue;

            BigDecimal totalValue = BigDecimal.ZERO;
            BigDecimal totalCostBasis = BigDecimal.ZERO;
            BigDecimal totalUnrealizedPL = BigDecimal.ZERO;

            List<PortfolioPosition> positions =
                    positionRepository.findByPortfolioId(portfolio.getId());

            for (PortfolioPosition position : positions) {

                String symbol = position.getSymbol();
                BigDecimal quantity = position.getQuantity();
                BigDecimal avgBuyPrice = position.getAverageBuyPrice();

                // Fetch stock price for the snapshot date
                StockPrice stockPrice = stockPriceRepository
                        .findBySymbolAndPriceDate(symbol, priceDate)
                        .orElse(null);

                if (stockPrice == null) {
                    continue; // skip if no price available
                }

                BigDecimal marketPrice = stockPrice.getClosePrice();

                // totalValue += quantity * marketPrice
                BigDecimal positionValue = quantity.multiply(marketPrice);
                totalValue = totalValue.add(positionValue);

                // totalCostBasis += quantity * avgBuyPrice
                BigDecimal costBasis = quantity.multiply(avgBuyPrice);
                totalCostBasis = totalCostBasis.add(costBasis);

                // totalUnrealizedPL += (marketPrice - avgBuyPrice) * quantity
                BigDecimal unrealizedPL =
                        marketPrice.subtract(avgBuyPrice)
                                .multiply(quantity);

                totalUnrealizedPL = totalUnrealizedPL.add(unrealizedPL);
            }

            // Sum realized profits
            BigDecimal totalRealizedPL =
                    realizedProfitRepository
                            .sumRealizedProfitByPortfolioId(portfolio.getId());

            PortfolioSnapshot snapshot = new PortfolioSnapshot();
            snapshot.setPortfolioId(portfolio.getId());
            snapshot.setSnapshotDate(priceDate);
            snapshot.setTotalValue(totalValue);
            snapshot.setTotalCostBasis(totalCostBasis);
            snapshot.setTotalUnrealizedPl(totalUnrealizedPL);
            snapshot.setTotalRealizedPl(totalRealizedPL);
            snapshot.setCreatedAt(LocalDateTime.now());

            snapshotRepository.save(snapshot);
        }
    }

    // -- Query methods --
    public List<PortfolioSnapshot> getSnapshotsByPortfolio(Long portfolioId) {
        return snapshotRepository.findByPortfolioIdOrderBySnapshotDateDesc(portfolioId);
    }
}