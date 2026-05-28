package com.prototype.honda.api.analytics.service;

import com.prototype.honda.api.analytics.model.*;
import com.prototype.honda.api.business.repository.ProposalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ProposalAnalyticsService {

    private final ProposalRepository proposalAnalyticsRepository;

    public ProposalDashboardModel getDashboardMetrics() {
        return proposalAnalyticsRepository.getDashboardMetrics();
    }

    public Collection<ProposalBestOfferModel> findBestOffers() {
        return proposalAnalyticsRepository.findBestOffers();
    }

    public Collection<DealershipRankingModel> getDealershipRanking() {
        return proposalAnalyticsRepository.getDealershipRanking();
    }

    public Collection<RegionalMetricsModel> getRegionalMetrics() {
        return proposalAnalyticsRepository.getRegionalMetrics();
    }

    public Collection<VehicleRankingModel> getMostQuotedVehicles() {
        return proposalAnalyticsRepository.getMostQuotedVehicles();
    }
}
