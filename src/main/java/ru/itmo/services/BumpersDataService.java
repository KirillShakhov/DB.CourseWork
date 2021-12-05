package ru.itmo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.entity.Bumper;
import ru.itmo.repository.CustomizedBumpersCrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Основной сервис взаимодействия с H2
 * для WebResource
 */

@Service
public class BumpersDataService {
    private final CustomizedBumpersCrudRepository customizedBumpersCrudRepository;

    @Autowired
    public BumpersDataService(CustomizedBumpersCrudRepository customizedBumpersCrudRepository) {
        this.customizedBumpersCrudRepository = customizedBumpersCrudRepository;
    }

    @Transactional
    public List<Bumper> findAll() {
        return (List<Bumper>) customizedBumpersCrudRepository.findAll();
    }

    @Transactional
    public void save(Bumper color) {
        customizedBumpersCrudRepository.save(color);
    }

    @Transactional
    public Optional<Bumper> getById(Long bumpers) {
        return customizedBumpersCrudRepository.findById(bumpers);
    }

    @Transactional
    public void removeById(Long id) {
        customizedBumpersCrudRepository.deleteById(id);
    }

}

