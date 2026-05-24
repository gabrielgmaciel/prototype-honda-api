package com.prototype.honda.api.address.service;

import com.prototype.honda.api.address.client.AddressClient;
import com.prototype.honda.api.address.dto.AddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressClient addressClient;

    public AddressResponse findAddress(String zipCode) {
        return addressClient.getAddressByZipCode(zipCode);
    }
}
