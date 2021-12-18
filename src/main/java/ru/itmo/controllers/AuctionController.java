package ru.itmo.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.entity.*;
import ru.itmo.services.AuctionDataService;
import ru.itmo.services.ContractDataService;
import ru.itmo.services.ItemsDataService;
import ru.itmo.services.UserDataService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class AuctionController {
    private final ItemsDataService itemsDataService;
    private final UserDataService userDataService;
    private final AuctionDataService auctionDataService;
    private final ContractDataService contractDataService;

    @Autowired
    public AuctionController(UserDataService userDataService, ItemsDataService itemsDataService, AuctionDataService auctionDataService, ContractDataService contractDataService) {
        this.userDataService = userDataService;
        this.itemsDataService = itemsDataService;
        this.auctionDataService = auctionDataService;
        this.contractDataService = contractDataService;
    }

    @JsonView(View.Auction.class)
    @GetMapping("/api/v1/auction")
    public Map<String, Object> getAuction() {
        Map<String, Object> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            auctionDataService.updateAuctions();

            List<Auction> list = auctionDataService.findAll();
            list.removeIf(i -> i.getContract().is_closed());
            map.put("list", list);
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            e.printStackTrace();
            return map;
        }
    }

    @JsonView(View.Auction.class)
    @GetMapping("/api/v1/auction/items")
    public Map<String, Object> getAuction(@RequestParam("login") String login,
                                            @RequestParam("pass") String pass,
                                            @RequestParam("id") Long id) {
        Map<String, Object> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            auctionDataService.updateAuctions();

            Optional<User> user = userDataService.getByLogin(login);

            if (user.isEmpty()) throw new Exception("Аккаунта не существует");
            if (!user.get().getPass().equals(pass)) throw new Exception("Пароль неправильный");

            Optional<Auction> auction = auctionDataService.getById(id);
            if (auction.isEmpty()) throw new Exception("Аукцион не существует");

            map.put("list", auction.get().getContract().getItems());
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            e.printStackTrace();
            return map;
        }
    }

    @GetMapping("/api/v1/auction/create")
    public Map<String, String> createAuction(@RequestParam("login") String login,
                                              @RequestParam("pass") String pass,
                                              @RequestParam(value = "to_user", required = false) String to_user,
                                              @RequestParam("from_money") Integer from_money,
                                              @RequestParam("to_money") Integer to_money,
                                              @RequestParam("closing_date") String closing_date,
                                              @RequestParam(value = "closing_time", required = false) String closing_time,
                                              @RequestParam(value = "items", required = false) Long item,
                                              @RequestParam(value = "items", required = false) List<Long> items
    ) {
        Map<String, String> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            auctionDataService.updateAuctions();

            Optional<User> user = userDataService.getByLogin(login);
            if (user.isEmpty()) throw new Exception("Аккаунта не существует");
            if (!user.get().getPass().equals(pass)) throw new Exception("Пароль неправильный");

            Contract contract;
            if (to_user != null) {
                Optional<User> toUser = userDataService.getByLogin(to_user);
                System.out.println(to_user);
                if (toUser.isEmpty()) throw new Exception("Человек не найден (" + to_user + ")");
                contract = new Contract(user.get(), toUser.get(), from_money, to_money);
            } else {
                contract = new Contract(user.get(), from_money, to_money);
            }


            if(items != null) {
                for (Long i : items) {
                    Optional<Item> it = itemsDataService.getById(i);
                    if (it.isEmpty()) throw new Exception("Один из предметов не найден");
                    contract.getItems().add(it.get());
                }
            }
            else if(item != null){
                Optional<Item> it = itemsDataService.getById(item);
                if (it.isEmpty()) throw new Exception("Один из предметов не найден");
                contract.getItems().add(it.get());
            }
            else{
                throw new Exception("Предметы не выбраны");
            }
            LocalDate date = LocalDate.parse(closing_date);//"2018-05-05"
            Date d = convertToDateViaInstant(date);
            contract.setClosing_date(d);

            Auction auction = new Auction(contract);
            contractDataService.save(contract);
            auctionDataService.save(auction);
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @GetMapping("/api/v1/auction/remove")
    public Map<String, String> removeAuction(@RequestParam("login") String login,
                                              @RequestParam("pass") String pass,
                                              @RequestParam("id") Long id
    ) {
        Map<String, String> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            auctionDataService.updateAuctions();
            Optional<User> user = userDataService.getByLogin(login);
            if (user.isEmpty()) throw new Exception("Аккаунта не существует");
            if (!user.get().getPass().equals(pass)) throw new Exception("Пароль неправильный");

            Optional<Auction> auction = auctionDataService.getById(id);
            if (auction.isEmpty()) throw new Exception("Аукцион не найден");
            if (!auction.get().getContract().getFrom_user().getUsername().equals(user.get().getUsername()))
                throw new Exception("Аукцион вам не пренадлежит");
            auctionDataService.removeById(auction.get().getId());
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @GetMapping("/api/v1/auction/bet")
    public Map<String, String> betAuction(@RequestParam("login") String login,
                                              @RequestParam("pass") String pass,
                                              @RequestParam("id") Long id,
                                              @RequestParam("price") Integer price
    ) {
        Map<String, String> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            auctionDataService.updateAuctions();

            Optional<User> user = userDataService.getByLogin(login);
            if (user.isEmpty()) throw new Exception("Аккаунта не существует");
            if (!user.get().getPass().equals(pass)) throw new Exception("Пароль неправильный");

            Optional<Auction> auction = auctionDataService.getById(id);
            if (auction.isEmpty()) throw new Exception("Аукцион не найден");
            if(auction.get().getContract().is_closed()) throw new Exception("Аукцион закрыт");
            if(auction.get().getLast_bet_size() != null && auction.get().getLast_customer() != null) {
                if (auction.get().getLast_bet_size() != null && price < auction.get().getLast_bet_size())
                    throw new Exception("Ставка меньше предыдушей");
                if (auction.get().getLast_bet_size() == null && price < auction.get().getContract().getFrom_money())
                    throw new Exception("Ставка меньше назначенной");
                if (auction.get().getLast_bet_size() != null && auction.get().getLast_bet_size() >= auction.get().getContract().getTo_money())
                    throw new Exception("Прошлая ставка победила");
            }

            if(price >= auction.get().getContract().getFrom_money()){
                auctionDataService.buy(user.get(), price, id);
            }

            if(price > user.get().getBalance()) throw new Exception("Недостаточно денег");

            auction.get().setLast_bet_size(price);
            auction.get().setLast_customer(user.get());
            auctionDataService.save(auction.get());
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            e.printStackTrace();
            return map;
        }
    }

    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

//    @GetMapping("/api/v1/contract/buy")
//    public Map<String, String> confirmTrade(@RequestParam("login") String login,
//                                           @RequestParam("pass") String pass,
//                                           @RequestParam("id") Long id) {
//        Map<String, String> map = new ManagedMap<>();
//        map.put("status", "ok");
//        try {
//            Optional<User> user = userDataService.getByLogin(login);
//            if (user.isEmpty()) throw new Exception("Аккаунта не существует");
//            if (!user.get().getPass().equals(pass)) throw new Exception("Пароль неправильный");
//            Optional<PurchaseItems> item = tradeDataService.getById(id);
//            if (item.isEmpty()) throw new Exception("Предмет не найден");
//            if (item.get().getPrice() > user.get().getBalance()) throw new Exception("Недостаточно денег");
//            tradeDataService.buy(user.get(), item.get());
//            return map;
//        } catch (Exception e) {
//            map.put("status", "error");
//            map.put("message", e.getMessage());
//            return map;
//        }
//    }
}