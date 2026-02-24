package com.felipfad.fin_project.DTOs.Trade;

import java.math.BigDecimal;

public class TradeRequest {
    private String symbol;
    private BigDecimal quantity;
    private BigDecimal price; // optional - if null, use latest stock price

    public String getSymbol() { return symbol; }
    public BigDecimal getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }

    public void setSymbol(String symbol) { this.symbol = symbol; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
