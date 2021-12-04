package ru.itmo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.entity.Item;
import ru.itmo.entity.PurchaseItems;
import ru.itmo.entity.User;
import ru.itmo.repository.CustomizedItemsCrudRepository;
import ru.itmo.repository.CustomizedTradeCrudRepository;
import ru.itmo.repository.CustomizedUserCrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Основной сервис взаимодействия с H2
 * для WebResource
 */

@Service
public class TradeDataService {
    private final CustomizedUserCrudRepository customizedUserCrudRepository;
    private final CustomizedItemsCrudRepository customizedItemsCrudRepository;
    private final CustomizedTradeCrudRepository customizedTradeCrudRepository;

    @Autowired
    public TradeDataService(CustomizedUserCrudRepository customizedUserCrudRepository, CustomizedTradeCrudRepository customizedTradeCrudRepository, CustomizedItemsCrudRepository customizedItemsCrudRepository) {
        this.customizedItemsCrudRepository = customizedItemsCrudRepository;
        this.customizedUserCrudRepository = customizedUserCrudRepository;
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

    @Transactional
    public void buy(User user, PurchaseItems purchaseItems) {
        user.setBalance(user.getBalance()-purchaseItems.getPrice());
        User u2 = purchaseItems.getItem().getOwner();
        u2.setBalance(u2.getBalance()+purchaseItems.getPrice());
        Item i = purchaseItems.getItem();
        i.setOwner(user);
        i.setPurchase_items(null);
        customizedUserCrudRepository.save(user);
        customizedUserCrudRepository.save(u2);
        customizedTradeCrudRepository.save(purchaseItems);
        customizedTradeCrudRepository.delete(purchaseItems);
        customizedItemsCrudRepository.save(i);
    }
}

