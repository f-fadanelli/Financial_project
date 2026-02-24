package com.felipfad.fin_project.api.controller;

import com.felipfad.fin_project.data.consumer.PortfolioSnapshotService;
import com.felipfad.fin_project.model.PortfolioSnapshot;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/portfolios/{portfolioId}/snapshots")
public class PortfolioSnapshotController {

    private final PortfolioSnapshotService snapshotService;

    public PortfolioSnapshotController(PortfolioSnapshotService snapshotService) {
        this.snapshotService = snapshotService;
    }

    @GetMapping
    public ResponseEntity<List<PortfolioSnapshot>> getSnapshots(@PathVariable Long portfolioId) {
        List<PortfolioSnapshot> snapshots = snapshotService.getSnapshotsByPortfolio(portfolioId);
        return ResponseEntity.ok(snapshots);
    }
}
