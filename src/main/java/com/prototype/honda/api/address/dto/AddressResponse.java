package com.prototype.honda.api.address.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AddressResponse(
        String address,
        String city,
        String state,
        @JsonAlias("cep")
        String zipCode
) {
}
