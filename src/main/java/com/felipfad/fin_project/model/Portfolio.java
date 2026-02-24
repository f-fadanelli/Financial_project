package com.felipfad.fin_project.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "portfolios")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(nullable = false)
    private String name;

    @Column(name = "base_currency", nullable = false)
    private String baseCurrency = "USD";

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructors
    public Portfolio() {}

    public Portfolio(Client client, String name, String baseCurrency) {
        this.client = client;
        this.name = name;
        this.baseCurrency = baseCurrency;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public Client getClient() { return client; }
    public String getName() { return name; }
    public String getBaseCurrency() { return baseCurrency; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setClient(Client client) { this.client = client; }
    public void setName(String name) { this.name = name; }
    public void setBaseCurrency(String baseCurrency) { this.baseCurrency = baseCurrency; }
}