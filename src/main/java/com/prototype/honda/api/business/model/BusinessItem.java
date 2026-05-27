package com.prototype.honda.api.business.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessItem {

    private BigDecimal quantity;
    private BigDecimal cashPayment;
    private BigDecimal installment;
    private BigDecimal discount;
}
