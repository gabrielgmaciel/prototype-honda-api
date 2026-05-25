package com.prototype.honda.api.address.client;

import com.prototype.honda.api.address.dto.AddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AddressClient {

    private final RestTemplate restAddress;

    public AddressResponse getAddressByZipCode(String zipCode) {
        try {
            return restAddress.getForObject("/{zipCode}", AddressResponse.class, zipCode);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível obter o endereço", e);
        }
    }

}
