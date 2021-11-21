package ru.itmo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.entity.Creator;
import ru.itmo.entity.User;
import ru.itmo.repository.CustomizedCreatorCrudRepository;
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
    private final CustomizedCreatorCrudRepository customizedCreatorCrudRepository;

    @Autowired
    public UserDataService(CustomizedUserCrudRepository customizedUserCrudRepository, CustomizedCreatorCrudRepository customizedCreatorCrudRepository) {
        this.customizedUserCrudRepository = customizedUserCrudRepository;
        this.customizedCreatorCrudRepository = customizedCreatorCrudRepository;
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
    public void saveCreator(User user) {
        customizedCreatorCrudRepository.save(new Creator(user));
    }

    @Transactional
    public Optional<User> getByLogin(String username) {
        return customizedUserCrudRepository.findByLogin(username);
    }


}

