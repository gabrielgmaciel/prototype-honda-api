package com.prototype.honda.api.business.service;

import com.prototype.honda.api.business.client.BusinessClient;
import com.prototype.honda.api.business.code.service.GenerateCodeService;
import com.prototype.honda.api.business.model.ProposalModel;
import com.prototype.honda.api.business.repository.DealershipRepository;
import com.prototype.honda.api.business.repository.ProposalRepository;
import com.prototype.honda.api.cars.service.CarsService;
import com.prototype.honda.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private final ProposalRepository proposalRepository;
    private final DealershipRepository dealershipRepository;
    private final CarsService carsService;
    private final UserService userService;
    private final GenerateCodeService generateCodeService;
    private final BusinessClient businessClient;
    private final TaskExecutor taskExecutor;

    private final ExecutorService executor =
            Executors.newCachedThreadPool();

    public SseEmitter generateProposal(String userCode, String carId) {

        SseEmitter emitter = new SseEmitter(300000L);
        CompletableFuture.runAsync(() -> {
            try {
                var proposals = getProposals(userCode, carId);
                var futures = proposals.stream()
                        .map(proposal ->
                                CompletableFuture.supplyAsync(() -> {
                                    ProposalModel generated = businessClient.generateProposal(proposal);
                                    ProposalModel saved = proposalRepository.save(generated);

                                    try {
                                        synchronized (emitter) {
                                            emitter.send(SseEmitter.event()
                                                    .name("proposal")
                                                    .data(saved));
                                        }
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                    return saved;
                                }, taskExecutor)
                        )
                        .toList();

                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                        .join();

                synchronized (emitter) {
                    emitter.send(SseEmitter.event()
                            .name("finished")
                            .data("Processamento finalizado"));
                }

                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        }, taskExecutor);

        return emitter;
    }

    private Collection<ProposalModel> getProposals(String userCode, String carId) {

        var proposalCode = generateCodeService.generateProposalCode();
        var car = carsService.findById(carId);
        var user = userService.getUserById(userCode);

        return dealershipRepository.findAllByStatusActive()
                .stream()
                .map(partner -> ProposalModel.builder()
                        .proposalCode(proposalCode)
                        .customerCode(user.getId())
                        .customerName(user.getName())
                        .customerEmail(user.getEmail())
                        .vehicleModelName(car.name())
                        .vehiclePrice(car.price())
                        .createdAt(LocalDateTime.now())
                        .finishedAt(LocalDateTime.now().minusDays(7))
                        .dealership(partner)
                        .build())
                .map(proposalRepository::save)
                .toList();
    }
}