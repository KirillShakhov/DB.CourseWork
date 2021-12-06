package ru.itmo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.entity.Auction;
import ru.itmo.repository.CustomizedAuctionCrudRepository;
import ru.itmo.repository.CustomizedContractCrudRepository;
import ru.itmo.repository.CustomizedUserCrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Основной сервис взаимодействия с H2
 * для WebResource
 */

@Service
public class AuctionDataService {
    private final CustomizedContractCrudRepository customizedContractCrudRepository;
    private final CustomizedAuctionCrudRepository customizedAuctionCrudRepository;
    private final CustomizedUserCrudRepository customizedUserCrudRepository;

    @Autowired
    public AuctionDataService(CustomizedContractCrudRepository customizedContractCrudRepository, CustomizedAuctionCrudRepository customizedAuctionCrudRepository, CustomizedUserCrudRepository customizedUserCrudRepository) {
        this.customizedContractCrudRepository = customizedContractCrudRepository;
        this.customizedAuctionCrudRepository = customizedAuctionCrudRepository;
        this.customizedUserCrudRepository = customizedUserCrudRepository;
    }


    @Transactional
    public List<Auction> findAll() {
        return (List<Auction>) customizedAuctionCrudRepository.findAll();
    }

    @Transactional
    public void save(Auction item) {
        customizedAuctionCrudRepository.save(item);
    }

    @Transactional
    public void removeById(Long id) {
        customizedAuctionCrudRepository.deleteById(id);
    }

    @Transactional
    public Optional<Auction> getById(Long item) {
        return customizedAuctionCrudRepository.findById(item);
    }

//    @Transactional
//    public void confirm(User user, Long id) throws Exception {
//        Optional<Contract> contract = customizedContractCrudRepository.findById(id);
//        if(contract.isEmpty()) throw new Exception("Контракт не найден");
//        User from_user = contract.get().getFrom_user();
//        from_user.setBalance(from_user.getBalance()-contract.get().getFrom_money());
//        from_user.setBalance(from_user.getBalance()+contract.get().getTo_money());
//
//        user.setBalance(user.getBalance()+contract.get().getFrom_money());
//        user.setBalance(user.getBalance()-contract.get().getTo_money());
//
//        contract.get().getItems().forEach(i -> {
//            i.setOwner(user);
//        });
//        customizedUserCrudRepository.save(from_user);
//        customizedUserCrudRepository.save(user);
//        customizedContractCrudRepository.save(contract.get());
//    }
}

