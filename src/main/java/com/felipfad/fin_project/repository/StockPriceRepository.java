package com.felipfad.fin_project.repository;

import com.felipfad.fin_project.model.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

import java.util.Optional;
import java.util.List;


@Repository
public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {

    List<StockPrice> findBySymbol(String symbol);

    Optional<StockPrice> findBySymbolAndPriceDate(String symbol, LocalDate priceDate);

    List<StockPrice> findByPriceDate(LocalDate priceDate);

}
