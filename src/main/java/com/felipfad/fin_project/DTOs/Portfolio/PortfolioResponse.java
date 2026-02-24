package com.felipfad.fin_project.DTOs.Portfolio;

import java.time.LocalDateTime;

public class PortfolioResponse {

    private Long id;
    private String name;
    private String baseCurrency;
    private LocalDateTime createdAt;

    private Long clientId;
    private String clientFullName;
    private String clientEmail;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBaseCurrency() { return baseCurrency; }
    public void setBaseCurrency(String baseCurrency) { this.baseCurrency = baseCurrency; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public String getClientFullName() { return clientFullName; }
    public void setClientFullName(String clientFullName) { this.clientFullName = clientFullName; }

    public String getClientEmail() { return clientEmail; }
    public void setClientEmail(String clientEmail) { this.clientEmail = clientEmail; }
    
}

