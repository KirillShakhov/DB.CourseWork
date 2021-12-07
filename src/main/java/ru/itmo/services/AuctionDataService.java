package ru.itmo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.entity.Auction;
import ru.itmo.entity.User;
import ru.itmo.repository.CustomizedAuctionCrudRepository;
import ru.itmo.repository.CustomizedContractCrudRepository;
import ru.itmo.repository.CustomizedItemsCrudRepository;
import ru.itmo.repository.CustomizedUserCrudRepository;

import javax.transaction.Transactional;
import java.sql.Time;
import java.util.Date;
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
    private final CustomizedItemsCrudRepository customizedItemsCrudRepository;
    private final CustomizedUserCrudRepository customizedUserCrudRepository;

    @Autowired
    public AuctionDataService(CustomizedContractCrudRepository customizedContractCrudRepository, CustomizedAuctionCrudRepository customizedAuctionCrudRepository, CustomizedUserCrudRepository customizedUserCrudRepository, CustomizedItemsCrudRepository customizedItemsCrudRepository) {
        this.customizedContractCrudRepository = customizedContractCrudRepository;
        this.customizedAuctionCrudRepository = customizedAuctionCrudRepository;
        this.customizedUserCrudRepository = customizedUserCrudRepository;
        this.customizedItemsCrudRepository = customizedItemsCrudRepository;
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

    @Transactional
    public void updateAuctions() {
        try {
            List<Auction> auction = findAll();
            Date date = new Date(System.currentTimeMillis());
            Time time = new Time(System.currentTimeMillis());

            for (Auction i : auction) {
                if (!i.getContract().is_closed()) {
                    if (date.before(i.getContract().getClosing_date()) && time.before(i.getContract().getClosing_time())) {
                        if (i.getLast_customer() != null) {
                            i.getContract().getItems().forEach(r -> {
                                r.setOwner(i.getLast_customer());
                                customizedItemsCrudRepository.save(r);
                            });

                            i.getLast_customer().setBalance(i.getLast_customer().getBalance() - i.getLast_bet_size());
                            i.getContract().getFrom_user().setBalance(i.getContract().getFrom_user().getBalance() + i.getLast_bet_size());
                            customizedUserCrudRepository.save(i.getLast_customer());
                            customizedUserCrudRepository.save(i.getContract().getFrom_user());
                            i.getContract().set_closed(true);
                            customizedAuctionCrudRepository.deleteById(i.getId());
                            customizedContractCrudRepository.delete(i.getContract());
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void buy(User user, Integer price, Long id) throws Exception {
        Optional<Auction> auction = getById(id);
        if(auction.isEmpty()) throw new Exception("Аукцион не наден");
        Auction i = auction.get();
        i.getContract().getItems().forEach(r -> {
            r.setOwner(user);
            customizedItemsCrudRepository.save(r);
        });


        user.setBalance(user.getBalance()-price);
        i.getContract().getFrom_user().setBalance(i.getContract().getFrom_user().getBalance()+price);
        customizedUserCrudRepository.save(user);
        customizedUserCrudRepository.save(i.getContract().getFrom_user());
        i.getContract().set_closed(true);
        customizedContractCrudRepository.save(i.getContract());
        customizedAuctionCrudRepository.deleteById(i.getId());
        customizedContractCrudRepository.delete(i.getContract());
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

