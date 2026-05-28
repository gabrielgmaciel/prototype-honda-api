package com.prototype.honda.api.analytics.model;

import java.math.BigDecimal;

public record DealershipRankingModel(
        String dealershipId,
        String dealershipName,
        String state,

        BigDecimal avgDiscount,
        BigDecimal avgInstallment,
        BigDecimal avgCashPayment,

        Long proposals,

        BigDecimal score
) {
}
