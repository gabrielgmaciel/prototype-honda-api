package com.prototype.honda.api.address.controller;

import com.prototype.honda.api.address.dto.AddressResponse;
import com.prototype.honda.api.address.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/addresses")
@Tag(name = "Addresses", description = "Endpoints relacionados à gestão de endereços")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<AddressResponse> findAddress(@RequestParam String zipCode) {
        return ResponseEntity.ok(addressService.findAddress(zipCode));
    }

}
