package ru.itmo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.entity.Color;
import ru.itmo.repository.CustomizedColorsCrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Основной сервис взаимодействия с H2
 * для WebResource
 */

@Service
public class ColorsDataService {
    private final CustomizedColorsCrudRepository customizedColorsCrudRepository;

    @Autowired
    public ColorsDataService(CustomizedColorsCrudRepository customizedColorsCrudRepository) {
        this.customizedColorsCrudRepository = customizedColorsCrudRepository;
    }

    @Transactional
    public List<Color> findAll() {
        return (List<Color>) customizedColorsCrudRepository.findAll();
    }

    @Transactional
    public void save(Color color) {
        customizedColorsCrudRepository.save(color);
    }
}
