package com.felipfad.fin_project.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "portfolio_snapshots",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_snapshot_portfolio_date",
                columnNames = {"portfolio_id", "snapshot_date"})
    },
    indexes = {
        @Index(name = "idx_snapshots_portfolio_date",
                columnList = "portfolio_id, snapshot_date DESC")
    }
)
public class PortfolioSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "portfolio_id", nullable = false)
    private Long portfolioId;

    @Column(name = "snapshot_date", nullable = false)
    private LocalDate snapshotDate;

    @Column(name = "total_value", nullable = false, precision = 19, scale = 4)
    private BigDecimal totalValue;

    @Column(name = "total_cost_basis", nullable = false, precision = 19, scale = 4)
    private BigDecimal totalCostBasis;

    @Column(name = "total_unrealized_pl", nullable = false, precision = 19, scale = 4)
    private BigDecimal totalUnrealizedPl;

    @Column(name = "total_realized_pl", nullable = false, precision = 19, scale = 4)
    private BigDecimal totalRealizedPl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // ===== Constructors =====

    public PortfolioSnapshot() {}

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public LocalDate getSnapshotDate() {
        return snapshotDate;
    }

    public void setSnapshotDate(LocalDate snapshotDate) {
        this.snapshotDate = snapshotDate;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public BigDecimal getTotalCostBasis() {
        return totalCostBasis;
    }

    public void setTotalCostBasis(BigDecimal totalCostBasis) {
        this.totalCostBasis = totalCostBasis;
    }

    public BigDecimal getTotalUnrealizedPl() {
        return totalUnrealizedPl;
    }

    public void setTotalUnrealizedPl(BigDecimal totalUnrealizedPl) {
        this.totalUnrealizedPl = totalUnrealizedPl;
    }

    public BigDecimal getTotalRealizedPl() {
        return totalRealizedPl;
    }

    public void setTotalRealizedPl(BigDecimal totalRealizedPl) {
        this.totalRealizedPl = totalRealizedPl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}