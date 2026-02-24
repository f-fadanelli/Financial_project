package com.felipfad.fin_project.data.provider;

import java.util.List;

import com.felipfad.fin_project.DTOs.StockPrice.StockPriceDto;

public interface MarketDataProviderInterface {

    List<StockPriceDto> fetchDailyPrices();

}