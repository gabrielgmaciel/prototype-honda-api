package com.prototype.honda.api.business.controller;

import com.prototype.honda.api.auth.dto.UserPrincipal;
import com.prototype.honda.api.business.service.BusinessService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/business")
@Tag(name = "Business", description = "Endpoints relacionados à gestão de negócios")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class BusinessController {

    private final BusinessService businessService;

    @PostMapping(value = "/new/proposal/{carId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> generateProposal(
            @PathVariable String carId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok()
                .header("Cache-Control", "no-cache")
                .header("X-Accel-Buffering", "no")
                .body(businessService.generateProposal(userPrincipal.getUser().getId(), carId));
    }
}
