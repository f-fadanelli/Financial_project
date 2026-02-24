package com.felipfad.fin_project.DTOs.Portfolio;

public class PortfolioRequest {

    private Long clientId;
    private String name;
    private String baseCurrency;

    // Getters & Setters
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBaseCurrency() { return baseCurrency; }
    public void setBaseCurrency(String baseCurrency) { this.baseCurrency = baseCurrency; }
}
