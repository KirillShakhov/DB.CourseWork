package ru.itmo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.entity.Wheels;
import ru.itmo.repository.CustomizedWheelsCrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Основной сервис взаимодействия с H2
 * для WebResource
 */

@Service
public class WheelsDataService {
    private final CustomizedWheelsCrudRepository customizedWheelsCrudRepository;

    @Autowired
    public WheelsDataService(CustomizedWheelsCrudRepository customizedWheelsCrudRepository) {
        this.customizedWheelsCrudRepository = customizedWheelsCrudRepository;
    }

    @Transactional
    public List<Wheels> findAll() {
        return (List<Wheels>) customizedWheelsCrudRepository.findAll();
    }

    @Transactional
    public void save(Wheels color) {
        customizedWheelsCrudRepository.save(color);
    }
}

