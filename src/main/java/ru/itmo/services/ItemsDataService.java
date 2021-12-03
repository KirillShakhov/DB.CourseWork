package ru.itmo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.entity.Item;
import ru.itmo.entity.User;
import ru.itmo.repository.CustomizedItemsCrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Основной сервис взаимодействия с H2
 * для WebResource
 */

@Service
public class ItemsDataService {
    private final CustomizedItemsCrudRepository customizedItemsCrudRepository;

    @Autowired
    public ItemsDataService(CustomizedItemsCrudRepository customizedItemsCrudRepository) {
        this.customizedItemsCrudRepository = customizedItemsCrudRepository;
    }

    @Transactional
    public List<Item> findAll() {
        return (List<Item>) customizedItemsCrudRepository.findAll();
    }

    @Transactional
    public void save(Item item) {
        customizedItemsCrudRepository.save(item);
    }

    @Transactional
    public Optional<Item> getById(Long item) {
        return customizedItemsCrudRepository.findById(item);
    }

    public List<Item> findAllByUser(User user) {
        return customizedItemsCrudRepository.findByUser(user.getId_user());
    }
}

