package com.prototype.honda.api.analytics.repository.impl;

import com.prototype.honda.api.analytics.model.*;
import com.prototype.honda.api.analytics.repository.ProposalAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;

import java.util.Collection;

@RequiredArgsConstructor
public class ProposalAnalyticsRepositoryImpl implements ProposalAnalyticsRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public ProposalDashboardModel getDashboardMetrics() {

        Aggregation aggregation = Aggregation.newAggregation(

                Aggregation.group()
                        .count().as("totalProposals")

                        .avg("businessItem.discount")
                        .as("avgDiscount")

                        .avg("businessItem.installment")
                        .as("avgInstallment")

                        .avg("businessItem.cashPayment")
                        .as("avgCashPayment")

                        .max("businessItem.discount")
                        .as("biggestDiscount")

                        .min("businessItem.installment")
                        .as("lowestInstallment")

                        .min("businessItem.cashPayment")
                        .as("lowestCashPayment")
        );

        return mongoTemplate.aggregate(
                aggregation,
                "proposals",
                ProposalDashboardModel.class
        ).getUniqueMappedResult();
    }

    @Override
    public Collection<ProposalBestOfferModel> findBestOffers() {

        Aggregation aggregation = Aggregation.newAggregation(

                Aggregation.project()

                        .and("dealership.name")
                        .as("dealershipName")

                        .and("dealership.state")
                        .as("state")

                        .and("businessItem.installment")
                        .as("installment")

                        .and("businessItem.cashPayment")
                        .as("cashPayment")

                        .and("businessItem.discount")
                        .as("discount")

                        .andExpression(
                                "(businessItem.installment * businessItem.quantity) + businessItem.cashPayment"
                        ).as("totalFinanced")

                        .andExpression(
                                "(businessItem.discount / vehiclePrice) * 100"
                        ).as("discountPercent"),

                Aggregation.sort(
                        Sort.by(Sort.Direction.DESC, "discount")
                )
        );

        return mongoTemplate.aggregate(
                aggregation,
                "proposals",
                ProposalBestOfferModel.class
        ).getMappedResults();
    }

    @Override
    public Collection<DealershipRankingModel> getDealershipRanking() {

        Aggregation aggregation = Aggregation.newAggregation(

                Aggregation.group("dealership.id")

                        .first("dealership.id")
                        .as("dealershipId")

                        .first("dealership.name")
                        .as("dealershipName")

                        .first("dealership.state")
                        .as("state")

                        .avg("businessItem.discount")
                        .as("avgDiscount")

                        .avg("businessItem.installment")
                        .as("avgInstallment")

                        .avg("businessItem.cashPayment")
                        .as("avgCashPayment")

                        .count()
                        .as("proposals")

                        .avg(
                                ArithmeticOperators.Divide.valueOf(
                                        "businessItem.discount"
                                ).divideBy("vehiclePrice")
                        ).as("score"),

                Aggregation.sort(
                        Sort.by(Sort.Direction.DESC, "score")
                )
        );

        return mongoTemplate.aggregate(
                aggregation,
                "proposals",
                DealershipRankingModel.class
        ).getMappedResults();
    }

    @Override
    public Collection<RegionalMetricsModel> getRegionalMetrics() {

        Aggregation aggregation = Aggregation.newAggregation(

                Aggregation.group("dealership.state")

                        .first("dealership.state")
                        .as("state")

                        .avg("businessItem.discount")
                        .as("avgDiscount")

                        .avg("businessItem.installment")
                        .as("avgInstallment")

                        .avg("businessItem.cashPayment")
                        .as("avgCashPayment")

                        .min("businessItem.installment")
                        .as("minInstallment")

                        .max("businessItem.discount")
                        .as("maxDiscount")

                        .count()
                        .as("proposals"),

                Aggregation.sort(
                        Sort.by(Sort.Direction.DESC, "avgDiscount")
                )
        );

        return mongoTemplate.aggregate(
                aggregation,
                "proposals",
                RegionalMetricsModel.class
        ).getMappedResults();
    }

    @Override
    public Collection<VehicleRankingModel> getMostQuotedVehicles() {

        Aggregation aggregation = Aggregation.newAggregation(

                Aggregation.group("vehicleModelName")

                        .first("vehicleModelName")
                        .as("vehicleModelName")

                        .count()
                        .as("proposals")

                        .avg("businessItem.discount")
                        .as("avgDiscount")

                        .avg("businessItem.installment")
                        .as("avgInstallment")

                        .avg("vehiclePrice")
                        .as("avgVehiclePrice")

                        .max("businessItem.discount")
                        .as("biggestDiscount")

                        .min("businessItem.installment")
                        .as("lowestInstallment"),

                Aggregation.sort(
                        Sort.by(Sort.Direction.DESC, "proposals")
                )
        );

        return mongoTemplate.aggregate(
                aggregation,
                "proposals",
                VehicleRankingModel.class
        ).getMappedResults();
    }
}
