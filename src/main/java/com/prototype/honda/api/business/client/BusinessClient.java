package com.prototype.honda.api.business.client;

import com.prototype.honda.api.business.model.ProposalModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class BusinessClient {

    private final RestTemplate restBusinessPartner;

    public ProposalModel generateProposal(ProposalModel request) {
        try {
            return restBusinessPartner.postForObject("/api/business/generate/proposal", request, ProposalModel.class);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível gerar a proposta", e);
        }

    }
}
