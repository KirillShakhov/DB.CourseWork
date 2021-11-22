package ru.itmo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.entity.Car;
import ru.itmo.entity.Series;
import ru.itmo.repository.CustomizedCarsCrudRepository;
import ru.itmo.repository.CustomizedSeriesCrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Основной сервис взаимодействия с H2
 * для WebResource
 */

@Service
public class SeriesDataService {
    private final CustomizedSeriesCrudRepository customizedSeriesCrudRepository;
    private final CustomizedCarsCrudRepository customizedCarsCrudRepository;

    @Autowired
    public SeriesDataService(CustomizedSeriesCrudRepository customizedSeriesCrudRepository, CustomizedCarsCrudRepository customizedCarsCrudRepository) {
        this.customizedSeriesCrudRepository = customizedSeriesCrudRepository;
        this.customizedCarsCrudRepository = customizedCarsCrudRepository;
    }

    @Transactional
    public List<Series> findAll() {
        return (List<Series>) customizedSeriesCrudRepository.findAll();
    }

    @Transactional
    public void save(Series series) {
        customizedSeriesCrudRepository.save(series);
    }

    @Transactional
    public void saveCar(Car car) {
        customizedCarsCrudRepository.save(car);
    }
}

