package com.felipfad.fin_project.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trades")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    private String symbol;

    @Enumerated(EnumType.STRING)
    @Column(name = "trade_type")
    private TradeType tradeType;

    private BigDecimal quantity;

    private BigDecimal price;

    @Column(name = "executed_at")
    private LocalDateTime executedAt = LocalDateTime.now();

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Trade() {}

    public Trade(Portfolio portfolio, String symbol, TradeType tradeType, BigDecimal quantity, BigDecimal price) {
        this.portfolio = portfolio;
        this.symbol = symbol;
        this.tradeType = tradeType;
        this.quantity = quantity;
        this.price = price;
        this.executedAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Portfolio getPortfolio() { return portfolio; }
    public String getSymbol() { return symbol; }
    public TradeType getTradeType() { return tradeType; }
    public java.math.BigDecimal getQuantity() { return quantity; }
    public java.math.BigDecimal getPrice() { return price; }
    public LocalDateTime getExecutedAt() { return executedAt; }

    public void setPortfolio(Portfolio portfolio) { this.portfolio = portfolio; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public void setTradeType(TradeType tradeType) { this.tradeType = tradeType; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setExecutedAt(LocalDateTime executedAt) { this.executedAt = executedAt; }
}
