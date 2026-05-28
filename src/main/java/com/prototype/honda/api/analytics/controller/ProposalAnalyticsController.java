package com.prototype.honda.api.analytics.controller;

import com.prototype.honda.api.analytics.model.*;
import com.prototype.honda.api.analytics.service.ProposalAnalyticsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/analytics/proposals")
@Tag(name = "Proposal Analytics", description = "Endpoints relacionados à análise de propostas")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProposalAnalyticsController {

    private final ProposalAnalyticsService proposalAnalyticsService;

    @GetMapping("/dashboard")
    public ResponseEntity<ProposalDashboardModel> dashboard() {
        return ResponseEntity.ok(proposalAnalyticsService.getDashboardMetrics());
    }

    @GetMapping("/best/offers")
    public ResponseEntity<Collection<ProposalBestOfferModel>> bestOffers() {
        return ResponseEntity.ok(proposalAnalyticsService.findBestOffers());
    }

    @GetMapping("/ranking")
    public ResponseEntity<Collection<DealershipRankingModel>> ranking() {
        return ResponseEntity.ok(proposalAnalyticsService.getDealershipRanking());
    }

    @GetMapping("/regional")
    public ResponseEntity<Collection<RegionalMetricsModel>> regional() {
        return ResponseEntity.ok(proposalAnalyticsService.getRegionalMetrics());
    }

    @GetMapping("/most/quoted/vehicles")
    public ResponseEntity<Collection<VehicleRankingModel>> mostQuotedVehicles() {
        return ResponseEntity.ok(proposalAnalyticsService.getMostQuotedVehicles());
    }
}
