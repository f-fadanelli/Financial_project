package com.felipfad.fin_project.repository;

import com.felipfad.fin_project.model.RealizedProfit;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RealizedProfitRepository extends JpaRepository<RealizedProfit, Long> {

	java.util.List<RealizedProfit> findByPortfolioId(Long portfolioId);

	java.util.List<RealizedProfit> findByPortfolioIdAndSymbol(Long portfolioId, String symbol);

    // @Query("""
    //     SELECT COALESCE(SUM(r.realizedProfit), 0)
    //     FROM RealizedProfit r
    //     WHERE r.portfolioId = :portfolioId
    // """)
    // BigDecimal sumRealizedProfitByPortfolioId(Long portfolioId);

    @Query("""
        SELECT COALESCE(SUM(r.realizedProfit), 0)
        FROM RealizedProfit r
        WHERE r.portfolio.id = :portfolioId
    """)
    BigDecimal sumRealizedProfitByPortfolioId(@Param("portfolioId") Long portfolioId);

}
