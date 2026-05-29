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
public class ProposalAnalyticsRepositoryImpl
        implements ProposalAnalyticsRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public ProposalDashboardModel getDashboardMetrics() {

        Aggregation aggregation = Aggregation.newAggregation(

                Aggregation.group()

                        .count()
                        .as("totalProposals")

                        .avg("businessItem.discount")
                        .as("avgDiscountRaw")

                        .avg("businessItem.installment")
                        .as("avgInstallmentRaw")

                        .avg("businessItem.cashPayment")
                        .as("avgCashPaymentRaw")

                        .max("businessItem.discount")
                        .as("biggestDiscountRaw")

                        .min("businessItem.installment")
                        .as("lowestInstallmentRaw")

                        .min("businessItem.cashPayment")
                        .as("lowestCashPaymentRaw"),

                Aggregation.project()

                        .and("totalProposals")
                        .as("totalProposals")

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("avgDiscountRaw")
                                        .place(2)
                        ).as("avgDiscount")

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("avgInstallmentRaw")
                                        .place(2)
                        ).as("avgInstallment")

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("avgCashPaymentRaw")
                                        .place(2)
                        ).as("avgCashPayment")

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("biggestDiscountRaw")
                                        .place(2)
                        ).as("biggestDiscount")

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("lowestInstallmentRaw")
                                        .place(2)
                        ).as("lowestInstallment")

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("lowestCashPaymentRaw")
                                        .place(2)
                        ).as("lowestCashPayment")
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

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("businessItem.installment")
                                        .place(2)
                        ).as("installment")

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("businessItem.cashPayment")
                                        .place(2)
                        ).as("cashPayment")

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("businessItem.discount")
                                        .place(2)
                        ).as("discount")

                        .andExpression(
                                "(businessItem.installment * businessItem.quantity) + businessItem.cashPayment"
                        ).as("totalFinancedRaw")

                        .andExpression(
                                "(businessItem.discount / vehiclePrice) * 100"
                        ).as("discountPercentRaw"),

                Aggregation.project()

                        .andInclude(
                                "dealershipName",
                                "state",
                                "installment",
                                "cashPayment",
                                "discount"
                        )

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("totalFinancedRaw")
                                        .place(2)
                        ).as("totalFinanced")

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("discountPercentRaw")
                                        .place(2)
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

                Aggregation.group("dealership._id")

                        .first("dealership._id")
                        .as("dealershipId")

                        .first("dealership.name")
                        .as("dealershipName")

                        .first("dealership.state")
                        .as("state")

                        .avg("businessItem.discount")
                        .as("avgDiscountRaw")

                        .avg("businessItem.installment")
                        .as("avgInstallmentRaw")

                        .avg("businessItem.cashPayment")
                        .as("avgCashPaymentRaw")

                        .count()
                        .as("proposals")

                        .avg(
                                ArithmeticOperators.Divide.valueOf(
                                        "businessItem.discount"
                                ).divideBy("vehiclePrice")
                        ).as("scoreRaw"),

                Aggregation.project()

                        .andInclude(
                                "dealershipId",
                                "dealershipName",
                                "state",
                                "proposals"
                        )

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("avgDiscountRaw")
                                        .place(2)
                        ).as("avgDiscount")

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("avgInstallmentRaw")
                                        .place(2)
                        ).as("avgInstallment")

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("avgCashPaymentRaw")
                                        .place(2)
                        ).as("avgCashPayment")

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("scoreRaw")
                                        .place(4)
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
                        .as("avgDiscountRaw")

                        .avg("businessItem.installment")
                        .as("avgInstallmentRaw")

                        .avg("businessItem.cashPayment")
                        .as("avgCashPaymentRaw")

                        .min("businessItem.installment")
                        .as("minInstallmentRaw")

                        .max("businessItem.discount")
                        .as("maxDiscountRaw")

                        .count()
                        .as("proposals"),

                Aggregation.project()

                        .andInclude(
                                "state",
                                "proposals"
                        )

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("avgDiscountRaw")
                                        .place(2)
                        ).as("avgDiscount")

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("avgInstallmentRaw")
                                        .place(2)
                        ).as("avgInstallment")

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("avgCashPaymentRaw")
                                        .place(2)
                        ).as("avgCashPayment")

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("minInstallmentRaw")
                                        .place(2)
                        ).as("minInstallment")

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("maxDiscountRaw")
                                        .place(2)
                        ).as("maxDiscount"),

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
                        .as("avgDiscountRaw")

                        .avg("businessItem.installment")
                        .as("avgInstallmentRaw")

                        .avg("vehiclePrice")
                        .as("avgVehiclePriceRaw")

                        .max("businessItem.discount")
                        .as("biggestDiscountRaw")

                        .min("businessItem.installment")
                        .as("lowestInstallmentRaw"),

                Aggregation.project()

                        .andInclude(
                                "vehicleModelName",
                                "proposals"
                        )

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("avgDiscountRaw")
                                        .place(2)
                        ).as("avgDiscount")

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("avgInstallmentRaw")
                                        .place(2)
                        ).as("avgInstallment")

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("avgVehiclePriceRaw")
                                        .place(2)
                        ).as("avgVehiclePrice")

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("biggestDiscountRaw")
                                        .place(2)
                        ).as("biggestDiscount")

                        .and(
                                ArithmeticOperators.Round
                                        .roundValueOf("lowestInstallmentRaw")
                                        .place(2)
                        ).as("lowestInstallment"),

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