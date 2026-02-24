package com.felipfad.fin_project.api.service;

import com.felipfad.fin_project.model.Portfolio;
import com.felipfad.fin_project.DTOs.Message.MessageResponse;
import com.felipfad.fin_project.DTOs.Portfolio.PortfolioRequest;
import com.felipfad.fin_project.DTOs.Portfolio.PortfolioResponse;
import com.felipfad.fin_project.model.Client;
import com.felipfad.fin_project.repository.PortfolioRepository;
import com.felipfad.fin_project.repository.ClientRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    private final ClientRepository clientRepository;

    public PortfolioService(PortfolioRepository portfolioRepository, ClientRepository clientRepository) {
        this.portfolioRepository = portfolioRepository;
        this.clientRepository = clientRepository;
    }

    // --- Public methods returning DTOs ---
    public PortfolioResponse getPortfolioById(Long id) {
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
        return mapToDTO(portfolio); // use helper here
    }

    public List<PortfolioResponse> getAllPortfolios() {
        return portfolioRepository.findAll()
                .stream()
                .map(this::mapToDTO) // use helper here
                .toList();
    }

    public List<Portfolio> getPortfoliosByClientId(Long clientId) {
        return portfolioRepository.findByClientId(clientId);
    }

    public PortfolioResponse createPortfolio(PortfolioRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Portfolio portfolio = new Portfolio();
        portfolio.setClient(client);
        portfolio.setName(request.getName());
        portfolio.setBaseCurrency(request.getBaseCurrency() != null ? request.getBaseCurrency() : "USD");

        Portfolio savedPortfolio = portfolioRepository.save(portfolio);
        return mapToDTO(savedPortfolio);
    }

    public PortfolioResponse updatePortfolio(Long id, PortfolioRequest request) {
        Portfolio existingPortfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        existingPortfolio.setName(request.getName());
        existingPortfolio.setBaseCurrency(request.getBaseCurrency() != null ? request.getBaseCurrency() : "USD");
        existingPortfolio.setClient(client);

        Portfolio updatedPortfolio = portfolioRepository.save(existingPortfolio);
        return mapToDTO(updatedPortfolio);
    }

    public MessageResponse deletePortfolio(Long id) {
        portfolioRepository.deleteById(id);
        return new MessageResponse("Portfolio deleted successfully");
    }

    private PortfolioResponse mapToDTO(Portfolio portfolio) {
        PortfolioResponse dto = new PortfolioResponse();
        dto.setId(portfolio.getId());
        dto.setName(portfolio.getName());
        dto.setBaseCurrency(portfolio.getBaseCurrency());
        dto.setCreatedAt(portfolio.getCreatedAt());
        dto.setClientId(portfolio.getClient().getId());
        dto.setClientFullName(portfolio.getClient().getFullName());
        dto.setClientEmail(portfolio.getClient().getEmail());
        return dto;
    }

}