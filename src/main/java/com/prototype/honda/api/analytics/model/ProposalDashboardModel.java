package com.prototype.honda.api.analytics.model;

import java.math.BigDecimal;

public record ProposalDashboardModel(
        Long totalProposals,

        BigDecimal avgDiscount,
        BigDecimal avgInstallment,
        BigDecimal avgCashPayment,

        BigDecimal biggestDiscount,
        BigDecimal lowestInstallment,
        BigDecimal lowestCashPayment
) {
}
