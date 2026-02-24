package com.felipfad.fin_project.repository;

import com.felipfad.fin_project.model.PortfolioPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface PortfolioPositionRepository extends JpaRepository<PortfolioPosition, Long> {
    Optional<PortfolioPosition> findByPortfolioIdAndSymbol(Long portfolioId, String symbol);
    List<PortfolioPosition> findByPortfolioId(Long portfolioId);
}
