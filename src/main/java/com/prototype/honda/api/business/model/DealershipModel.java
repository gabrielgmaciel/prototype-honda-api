package com.prototype.honda.api.business.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "dealerships")
public class DealershipModel {

    @Id
    private String id;
    private String imagem;
    private String partnerCode;
    private String name;
    private String email;
    private String document;
    private String address;
    private String phone;
    private String zipCode;
    private String city;
    private String state;
    private String status;
}
