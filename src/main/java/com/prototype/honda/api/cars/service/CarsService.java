package com.prototype.honda.api.cars.service;

import com.prototype.honda.api.cars.model.CarsAvaliables;
import com.prototype.honda.api.cars.model.CarsModel;
import com.prototype.honda.api.cars.repository.CarsRepository;
import com.prototype.honda.api.exception.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarsService {

    private final CarsRepository carsRepository;

    public CarsAvaliables getCarsAvaliables() {
        return new CarsAvaliables(
                "1",
                "Honda Civic",
                "O Honda Civic é um carro compacto que oferece uma combinação de estilo, desempenho e eficiência. Com um design moderno e aerodinâmico, o Civic é conhecido por sua confiabilidade e conforto. Ele vem equipado com uma variedade de recursos tecnológicos, como sistema de infotainment, conectividade Bluetooth e assistentes de condução avançados. O Honda Civic é uma escolha popular para aqueles que buscam um carro versátil e econômico para o dia a dia."
        );
    }

    public CarsModel create(CarsModel carsModel) {
        return carsRepository.save(carsModel);
    }

    public CarsModel update(CarsModel carsModel) {
        return carsRepository.save(carsModel);
    }

    public CarsModel findById(String id) {
        return carsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Veículo não encontrado"));
    }

    public void delete(String id) {
        var car = findById(id);
        carsRepository.delete(car);
    }

}
