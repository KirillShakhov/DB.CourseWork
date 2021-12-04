package ru.itmo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.entity.PurchaseItems;
import ru.itmo.repository.CustomizedItemsCrudRepository;
import ru.itmo.repository.CustomizedTradeCrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Основной сервис взаимодействия с H2
 * для WebResource
 */

@Service
public class TradeDataService {
    private final CustomizedItemsCrudRepository customizedItemsCrudRepository;
    private final CustomizedTradeCrudRepository customizedTradeCrudRepository;

    @Autowired
    public TradeDataService(CustomizedItemsCrudRepository customizedItemsCrudRepository, CustomizedTradeCrudRepository customizedTradeCrudRepository) {
        this.customizedItemsCrudRepository = customizedItemsCrudRepository;
        this.customizedTradeCrudRepository = customizedTradeCrudRepository;
    }

    @Transactional
    public List<PurchaseItems> findAll() {
        return (List<PurchaseItems>) customizedTradeCrudRepository.findAll();
    }

    @Transactional
    public void save(PurchaseItems item) {
        customizedTradeCrudRepository.save(item);
    }

    @Transactional
    public void removeById(Long id) {
        customizedTradeCrudRepository.deleteById(id);
    }

    @Transactional
    public Optional<PurchaseItems> getById(Long item) {
        return customizedTradeCrudRepository.findById(item);
    }
}

