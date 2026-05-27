package com.prototype.honda.api.business.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "proposals")
public class ProposalModel {

    @Id
    private String id;
    private String proposalCode;
    private String customerCode;
    private String customerName;
    private String customerEmail;
    private String vehicleModelName;
    private BigDecimal vehiclePrice;
    private DealershipModel dealership;
    private BusinessItem businessItem;
}
