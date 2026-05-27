package com.prototype.honda.api.business.controller;

import com.prototype.honda.api.auth.dto.UserPrincipal;
import com.prototype.honda.api.business.model.ProposalModel;
import com.prototype.honda.api.business.service.BusinessService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/business")
@Tag(name = "Business", description = "Endpoints relacionados à gestão de negócios")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class BusinessController {

    private final BusinessService businessService;

    @PostMapping("/new/proposal/{carId}")
    public ResponseEntity<Collection<ProposalModel>> generateProposal(
            @PathVariable String carId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(businessService.generateProposal(userPrincipal.getUser().getId(), carId));
    }
}
