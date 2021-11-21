package ru.itmo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.entity.User;
import ru.itmo.repository.CustomizedUserCrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Основной сервис взаимодействия с H2
 * для WebResource
 */

@Service
public class UserDataService {
    private final CustomizedUserCrudRepository customizedUserCrudRepository;

    @Autowired
    public UserDataService(CustomizedUserCrudRepository customizedUserCrudRepository) {
        this.customizedUserCrudRepository = customizedUserCrudRepository;
    }

    @Transactional
    public List<User> findAll() {
        return (List<User>) customizedUserCrudRepository.findAll();
    }

    @Transactional
    public void save(User user) {
        customizedUserCrudRepository.save(user);
    }

    @Transactional
    public Optional<User> getByLogin(String username) {
        return customizedUserCrudRepository.findByLogin(username);
    }
}

