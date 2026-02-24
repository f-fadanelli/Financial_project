package com.felipfad.fin_project.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "portfolio_positions", uniqueConstraints = @UniqueConstraint(columnNames = {"portfolio_id", "symbol"}))
public class PortfolioPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    private String symbol;

    private BigDecimal quantity;

    @Column(name = "average_buy_price")
    private BigDecimal averageBuyPrice;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public PortfolioPosition() {}

    public PortfolioPosition(Portfolio portfolio, String symbol, BigDecimal quantity, BigDecimal averageBuyPrice) {
        this.portfolio = portfolio;
        this.symbol = symbol;
        this.quantity = quantity;
        this.averageBuyPrice = averageBuyPrice;
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Portfolio getPortfolio() { return portfolio; }
    public String getSymbol() { return symbol; }
    public BigDecimal getQuantity() { return quantity; }
    public BigDecimal getAverageBuyPrice() { return averageBuyPrice; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setPortfolio(Portfolio portfolio) { this.portfolio = portfolio; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    public void setAverageBuyPrice(BigDecimal averageBuyPrice) { this.averageBuyPrice = averageBuyPrice; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
