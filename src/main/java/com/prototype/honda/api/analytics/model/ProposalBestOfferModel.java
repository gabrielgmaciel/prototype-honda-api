package com.prototype.honda.api.analytics.model;

import java.math.BigDecimal;

public record ProposalBestOfferModel(
        String dealershipName,
        String state,
        BigDecimal installment,
        BigDecimal cashPayment,
        BigDecimal discount,
        BigDecimal totalFinanced,
        BigDecimal discountPercent
) {
}
