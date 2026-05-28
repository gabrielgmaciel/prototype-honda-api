package com.prototype.honda.api.analytics.repository;

import com.prototype.honda.api.analytics.model.*;

import java.util.Collection;

public interface ProposalAnalyticsRepository {

    ProposalDashboardModel getDashboardMetrics();

    Collection<ProposalBestOfferModel> findBestOffers();

    Collection<DealershipRankingModel> getDealershipRanking();

    Collection<RegionalMetricsModel> getRegionalMetrics();

    Collection<VehicleRankingModel> getMostQuotedVehicles();
}
