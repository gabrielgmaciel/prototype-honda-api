package com.prototype.honda.api.cars.service;

import com.prototype.honda.api.cars.dto.CarsResponse;
import com.prototype.honda.api.cars.model.CarsAvaliables;
import com.prototype.honda.api.cars.model.CarsModel;
import com.prototype.honda.api.cars.repository.CarsRepository;
import com.prototype.honda.api.converter.CarsConverter;
import com.prototype.honda.api.converter.SimpleCarsResponse;
import com.prototype.honda.api.exception.exceptions.NotFoundException;
import com.prototype.honda.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

import static com.prototype.honda.api.utils.Utils.saveFile;

@Service
@RequiredArgsConstructor
public class CarsService {

    private final CarsRepository carsRepository;
    private final CarsConverter carsConverter;

    public CarsModel create(CarsModel request) {
        return carsRepository.save(request);
    }

    public CarsModel uploadBanner(MultipartFile image, String id) {
        var car = carsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Veículo não encontrado"));

        car.setBanner(saveFile(image));
        return carsRepository.save(car);
    }

    public CarsModel uploadCover(MultipartFile image, String id) {
        var car = carsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Veículo não encontrado"));

        car.setCover(saveFile(image));
        return carsRepository.save(car);
    }

    public CarsModel uploadImages(Collection<MultipartFile> images, String id) {
        var car = carsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Veículo não encontrado"));

        car.setImages(images.stream()
                .map(Utils::saveFile)
                .toList()
        );
        return carsRepository.save(car);
    }

    public CarsModel update(CarsModel carsModel) {
        var car = carsRepository.findById(carsModel.getId())
                .orElseThrow(() -> new NotFoundException("Veículo não encontrado"));

        carsModel.setImages(car.getImages());
        carsModel.setCover(car.getCover());
        carsModel.setBanner(car.getBanner());

        return carsRepository.save(carsModel);
    }

    public CarsResponse findById(String id) {
        return carsRepository.findById(id)
                .map(carsConverter::convert)
                .orElseThrow(() -> new NotFoundException("Veículo não encontrado"));
    }

    public Collection<SimpleCarsResponse>  findAll() {
        return carsRepository.findAll().stream()
                .map(carsConverter::convertSimple)
                .toList();
    }

    public void delete(String id) {
        var car = carsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Veículo não encontrado"));
        carsRepository.delete(car);
    }

}
