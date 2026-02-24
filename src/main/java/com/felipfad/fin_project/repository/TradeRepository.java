package com.felipfad.fin_project.repository;

import com.felipfad.fin_project.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
	List<Trade> findByPortfolioId(Long portfolioId);

	List<Trade> findByPortfolioIdAndSymbol(Long portfolioId, String symbol);

	List<Trade> findBySymbol(String symbol);
}
