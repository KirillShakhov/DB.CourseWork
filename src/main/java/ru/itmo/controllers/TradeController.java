package ru.itmo.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.entity.Item;
import ru.itmo.entity.PurchaseItems;
import ru.itmo.entity.User;
import ru.itmo.entity.View;
import ru.itmo.services.ItemsDataService;
import ru.itmo.services.TradeDataService;
import ru.itmo.services.UserDataService;

import java.util.Map;
import java.util.Optional;

@RestController
public class TradeController {
    private final ItemsDataService itemsDataService;
    private final UserDataService userDataService;
    private final TradeDataService tradeDataService;

    @Autowired
    public TradeController(UserDataService userDataService, ItemsDataService itemsDataService, TradeDataService tradeDataService) {
        this.userDataService = userDataService;
        this.itemsDataService = itemsDataService;
        this.tradeDataService = tradeDataService;
    }

    @JsonView(View.PurchaseItems.class)
    @GetMapping("/api/v1/trade")
    public Map<String, Object> getTrades(@RequestParam(value = "find", required = false) String find) {
        Map<String, Object> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            if (find == null) {
                map.put("list", tradeDataService.findAll());
            } else {
                map.put("list", tradeDataService.findAll());
            }
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @GetMapping("/api/v1/trade/create")
    public Map<String, String> createTrade(@RequestParam("login") String login,
                                           @RequestParam("pass") String pass,
                                           @RequestParam("id") Long id,
                                           @RequestParam("price") Integer price
    ) {
        Map<String, String> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            Optional<User> user = userDataService.getByLogin(login);
            if (user.isEmpty()) throw new Exception("Аккаунта не существует");
            if (!user.get().getPass().equals(pass)) throw new Exception("Пароль неправильный");

            Optional<Item> item = itemsDataService.getById(id);
            if (item.isEmpty()) throw new Exception("Предмет не найден");
            if (!item.get().getOwner().getId_user().equals(user.get().getId_user()))
                throw new Exception("Предмет вам не пренадлежит");
            if (item.get().getPurchase_items() != null) throw new Exception("Предмет уже на продаже");

            PurchaseItems purchaseItems = new PurchaseItems(item.get(), price);
            tradeDataService.save(purchaseItems);
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @GetMapping("/api/v1/trade/remove")
    public Map<String, String> removeTrade(@RequestParam("login") String login,
                                           @RequestParam("pass") String pass,
                                           @RequestParam("id") Long id
    ) {
        Map<String, String> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            Optional<User> user = userDataService.getByLogin(login);
            if (user.isEmpty()) throw new Exception("Аккаунта не существует");
            if (!user.get().getPass().equals(pass)) throw new Exception("Пароль неправильный");

            Optional<Item> item = itemsDataService.getById(id);
            if (item.isEmpty()) throw new Exception("Предмет не найден");
            if (!item.get().getOwner().getId_user().equals(user.get().getId_user()))
                throw new Exception("Предмет вам не пренадлежит");
            if (item.get().getPurchase_items() == null) throw new Exception("Предмет не выставлен на продажу");
            tradeDataService.removeById(item.get().getPurchase_items().getId());
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @GetMapping("/api/v1/trade/confirm")
    public Map<String, String> confirmTrade(@RequestParam("login") String login,
                                           @RequestParam("pass") String pass,
                                           @RequestParam("id") Long id) {
        Map<String, String> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            Optional<User> user = userDataService.getByLogin(login);
            if (user.isEmpty()) throw new Exception("Аккаунта не существует");
            if (!user.get().getPass().equals(pass)) throw new Exception("Пароль неправильный");
            Optional<PurchaseItems> item = tradeDataService.getById(id);
            if (item.isEmpty()) throw new Exception("Предмет не найден");
            if (item.get().getPrice() > user.get().getBalance()) throw new Exception("Недостаточно денег");
            tradeDataService.buy(user.get(), item.get());
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }
}