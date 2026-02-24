package com.felipfad.fin_project.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "realized_profits")
public class RealizedProfit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    private String symbol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_id", nullable = false)
    private Trade trade;

    @Column(name = "realized_profit")
    private BigDecimal realizedProfit;

    @Column(name = "realized_at")
    private LocalDateTime realizedAt = LocalDateTime.now();

    public RealizedProfit() {}

    public RealizedProfit(Portfolio portfolio, String symbol, Trade trade, BigDecimal realizedProfit) {
        this.portfolio = portfolio;
        this.symbol = symbol;
        this.trade = trade;
        this.realizedProfit = realizedProfit;
        this.realizedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Portfolio getPortfolio() { return portfolio; }
    public String getSymbol() { return symbol; }
    public Trade getTrade() { return trade; }
    public BigDecimal getRealizedProfit() { return realizedProfit; }
    public LocalDateTime getRealizedAt() { return realizedAt; }

    public void setPortfolio(Portfolio portfolio) { this.portfolio = portfolio; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public void setTrade(Trade trade) { this.trade = trade; }
    public void setRealizedProfit(BigDecimal realizedProfit) { this.realizedProfit = realizedProfit; }
    public void setRealizedAt(LocalDateTime realizedAt) { this.realizedAt = realizedAt; }
}
