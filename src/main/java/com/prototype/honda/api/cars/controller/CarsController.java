package com.prototype.honda.api.cars.controller;

import com.prototype.honda.api.cars.dto.CarsResponse;
import com.prototype.honda.api.cars.model.CarsModel;
import com.prototype.honda.api.cars.service.CarsService;
import com.prototype.honda.api.converter.SimpleCarsResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@RequestMapping("/api/cars")
@Tag(name = "Cars", description = "Endpoints relacionados à gestão de carros")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CarsController {

    private final CarsService carsService;

    @GetMapping
    public ResponseEntity<CarsResponse> findCarById(
            @RequestParam(name = "id") String id) {
        return ResponseEntity.ok(carsService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<SimpleCarsResponse>> findAllCars() {
        return ResponseEntity.ok(carsService.findAll());
    }

    @PostMapping
    public ResponseEntity<CarsModel> createVehicle(
            @RequestBody CarsModel carsModel
    ) {
        return ResponseEntity.ok(carsService.create(carsModel));
    }

    @PostMapping(path = "/upload/banner", consumes = "multipart/form-data")
    public ResponseEntity<CarsModel> uploadBanner(
            @RequestParam MultipartFile image,
            @RequestParam(name = "id") String id) {
        return ResponseEntity.ok(carsService.uploadBanner(image, id));
    }

    @PostMapping(path = "/upload/cover", consumes = "multipart/form-data")
    public ResponseEntity<CarsModel> uploadCover(
            @RequestParam MultipartFile image,
            @RequestParam(name = "id") String id) {
        return ResponseEntity.ok(carsService.uploadCover(image, id));
    }

    @PostMapping(path = "/upload/images", consumes = "multipart/form-data")
    public ResponseEntity<CarsModel> uploadImages(
            @RequestParam("images") Collection<MultipartFile> images,
            @RequestParam(name = "id") String id) {
        return ResponseEntity.ok(carsService.uploadImages(images, id));
    }

    @PutMapping
    public ResponseEntity<CarsModel> updateCar(
            @RequestBody CarsModel carsModel) {
        return ResponseEntity.ok(carsService.update(carsModel));
    }

    @DeleteMapping
    public ResponseEntity deleteCar(
            @RequestParam(name = "id") String id) {
        carsService.delete(id);
        return ResponseEntity.accepted().build();
    }

}
