package com.prototype.honda.api.business.service;

import com.prototype.honda.api.business.client.BusinessClient;
import com.prototype.honda.api.business.code.service.GenerateCodeService;
import com.prototype.honda.api.business.model.ProposalModel;
import com.prototype.honda.api.business.repository.DealershipRepository;
import com.prototype.honda.api.business.repository.ProposalRepository;
import com.prototype.honda.api.cars.service.CarsService;
import com.prototype.honda.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private final ProposalRepository proposalRepository;
    private final DealershipRepository dealershipRepository;
    private final CarsService carsService;
    private final UserService userService;
    private final GenerateCodeService generateCodeService;
    private final BusinessClient businessClient;

    public Collection<ProposalModel> generateProposal(String userCode, String carId) {
        return getProposals(userCode, carId).stream()
                .map(businessClient::generateProposal)
                .map(proposalRepository::save)
                .toList();
    }

    private Collection<ProposalModel> getProposals(String userCode, String carId) {
        var proposalCode = generateCodeService.generateProposalCode();
        var car = carsService.findById(carId);
        var user = userService.getUserById(userCode);
        return dealershipRepository.findAllByStatusActive().stream()
                .map(partner -> ProposalModel.builder()
                        .proposalCode(proposalCode)
                        .customerCode(user.getId())
                        .customerName(user.getName())
                        .customerEmail(user.getEmail())
                        .vehicleModelName(car.name())
                        .vehiclePrice(car.price())
                        .dealership(partner)
                        .build())
                .map(proposalRepository::save)
                .toList();
    }
}
