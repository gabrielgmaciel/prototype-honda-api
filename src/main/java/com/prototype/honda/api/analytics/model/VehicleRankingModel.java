package com.prototype.honda.api.analytics.model;

import java.math.BigDecimal;

public record VehicleRankingModel(
        String vehicleModelName,
        Long proposals,
        BigDecimal avgDiscount,
        BigDecimal avgInstallment,
        BigDecimal avgVehiclePrice,
        BigDecimal biggestDiscount,
        BigDecimal lowestInstallment
) {
}
