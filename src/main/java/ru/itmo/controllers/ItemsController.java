package ru.itmo.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.entity.*;
import ru.itmo.services.*;

import java.util.Map;
import java.util.Optional;

@RestController
public class ItemsController {
    private final SeriesCarsDataService seriesCarsDataService;
    private final ItemsDataService itemsDataService;
    private final UserDataService userDataService;
    private final BumpersDataService bumpersDataService;
    private final WheelsDataService wheelsDataService;
    private final ColorsDataService colorsDataService;

    @Autowired
    public ItemsController(SeriesCarsDataService seriesCarsDataService, UserDataService userDataService, BumpersDataService bumpersDataService, WheelsDataService wheelsDataService, ColorsDataService colorsDataService, ItemsDataService itemsDataService) {
        this.seriesCarsDataService = seriesCarsDataService;
        this.userDataService = userDataService;
        this.bumpersDataService = bumpersDataService;
        this.wheelsDataService = wheelsDataService;
        this.colorsDataService = colorsDataService;
        this.itemsDataService = itemsDataService;
    }

    @JsonView(View.Item.class)
    @GetMapping("/api/v1/items")
    public Map<String, Object> getItems( @RequestParam("login") String login,
                                         @RequestParam("pass") String pass,
                                         @RequestParam(value = "id", required = false) Long id) {
        Map<String, Object> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            Optional<User> user = userDataService.getByLogin(login);
            if (user.isEmpty()) throw new Exception("Аккаунта не существует");
            if (!user.get().getPass().equals(pass)) throw new Exception("Пароль неправильный");

            if(id == null) map.put("list", itemsDataService.findAllByUser(user.get()));
            else {
                Optional<Item> item = itemsDataService.getById(id);
                if(item.isEmpty()) throw new Exception("Предмета не существует");
                if(!item.get().getOwner().getId_user().equals(user.get().getId_user())) throw new Exception("Предмет вам не пренадлежит");
                map.put("list", itemsDataService.getById(id));
            }
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @GetMapping("/api/v1/items/create")
    public Map<String, String> createItem( @RequestParam("login") String login,
                                             @RequestParam("pass") String pass,
                                             @RequestParam(value = "id_car", required = false) Long id_car,
                                             @RequestParam(value = "id_bumper", required = false) Long id_bumper,
                                             @RequestParam(value = "id_wheels", required = false) Long id_wheels, @RequestParam("description") String description,
                                             @RequestParam("real_photo") String real_photo) {
        Map<String, String> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            Optional<User> user = userDataService.getByLogin(login);
            if (user.isEmpty()) throw new Exception("Аккаунта не существует");
            if (!user.get().getPass().equals(pass)) throw new Exception("Пароль неправильный");

            Item item = new Item(user.get(), description, real_photo);
            if(id_car != null){
                Optional<Car> car = seriesCarsDataService.getCarById(id_car);
                if(car.isEmpty()) throw new Exception("Машинка не найдена, возможно она была удалена");
                item.setCar(car.get());
                itemsDataService.save(item);
                return map;
            }
            if(id_bumper != null){
                Optional<Bumper> bumper = bumpersDataService.getById(id_bumper);
                if(bumper.isEmpty()) throw new Exception("Бампер не найден, возможно он была удален");
                item.setBumper(bumper.get());
                itemsDataService.save(item);
                return map;
            }
            if(id_wheels != null){
                Optional<Wheels> wheels = wheelsDataService.getById(id_wheels);
                if(wheels.isEmpty()) throw new Exception("Колеса не найдены, возможно они были удалены");
                item.setWheels(wheels.get());
                itemsDataService.save(item);
                return map;
            }
          throw new Exception("Нет id предмета");
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @GetMapping("/api/v1/items/remove")
    public Map<String, String> removeItem( @RequestParam("login") String login,
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
            if(item.isEmpty()) throw new Exception("Предмет не найден");
            if(!item.get().getOwner().getId_user().equals(user.get().getId_user())) throw new Exception("Предмет вам не пренадлежит");
            seriesCarsDataService.removeById(item.get().getId_item());
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }
}