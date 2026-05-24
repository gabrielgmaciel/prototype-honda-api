package com.prototype.honda.api.cars.controller;

import com.prototype.honda.api.cars.model.CarsModel;
import com.prototype.honda.api.cars.service.CarsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cars")
@Tag(name = "Cars", description = "Endpoints relacionados à gestão de carros")
@RequiredArgsConstructor
public class CarsController {

    private final CarsService carsService;

    @GetMapping
    public ResponseEntity<CarsModel> findCarById(
            @RequestParam(name = "id") String id) {
        return ResponseEntity.ok(carsService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CarsModel> createCar(
            @RequestBody CarsModel carsModel) {
        return ResponseEntity.ok(carsService.create(carsModel));
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
