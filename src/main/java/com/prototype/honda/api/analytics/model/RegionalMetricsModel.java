package com.prototype.honda.api.analytics.model;

import java.math.BigDecimal;

public record RegionalMetricsModel(
        String state,

        BigDecimal avgDiscount,
        BigDecimal avgInstallment,
        BigDecimal avgCashPayment,

        BigDecimal minInstallment,
        BigDecimal maxDiscount,

        Long proposals
) {
}
