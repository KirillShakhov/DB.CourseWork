package ru.itmo.controllers;

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

//    @JsonView(View.Series.class)
//    @GetMapping("/api/v1/cars/groups")
//    public Map<String, Object> getCarsGroup(@RequestParam(value = "id", required = false) Long id) {
//        Map<String, Object> map = new ManagedMap<>();
//        map.put("status", "ok");
//        try {
//            if(id == null) map.put("list", seriesCarsDataService.findAll());
//            else map.put("list", seriesCarsDataService.getById(id));
//            return map;
//        } catch (Exception e) {
//            map.put("status", "error");
//            map.put("message", e.getMessage());
//            return map;
//        }
//    }

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
            if (user.get().getCreator() == null) throw new Exception("Вы не являетесь создателем");

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
//
//    @GetMapping("/api/v1/cars/groups/remove")
//    public Map<String, String> removeCarsGroup( @RequestParam("login") String login,
//                                              @RequestParam("pass") String pass,
//                                              @RequestParam("id") Long id
//    ) {
//        Map<String, String> map = new ManagedMap<>();
//        map.put("status", "ok");
//        try {
//            Optional<User> user = userDataService.getByLogin(login);
//            if (user.isEmpty()) throw new Exception("Аккаунта не существует");
//            if (!user.get().getPass().equals(pass)) throw new Exception("Пароль неправильный");
//            if (user.get().getCreator() == null) throw new Exception("Вы не являетесь создателем");
//
//            Optional<Series> s = seriesCarsDataService.getById(id);
//            if(s.isEmpty()) throw new Exception("Серия не найдена");
//            if(!s.get().getCreator().getId_creators().equals(user.get().getCreator().getId_creators())) throw new Exception("Серия создана не вами");
//            seriesCarsDataService.removeById(s.get().getId_series());
//            return map;
//        } catch (Exception e) {
//            map.put("status", "error");
//            map.put("message", e.getMessage());
//            return map;
//        }
//    }
//
//    @GetMapping("/api/v1/cars/create")
//    public Map<String, String> createCar( @RequestParam("login") String login,
//                                                @RequestParam("pass") String pass,
//                                                @RequestParam("name") String name,
//                                                @RequestParam("series") Long series,
//                                                @RequestParam(value = "bumpers", required=false) Long bumpers,
//                                                @RequestParam(value = "wheels", required=false) Long wheels,
//                                                @RequestParam(value = "first_color", required=false) Long first_color,
//                                                @RequestParam(value = "second_color", required=false) Long second_color
//    ) {
//        Map<String, String> map = new ManagedMap<>();
//        map.put("status", "ok");
//        try {
//            Optional<User> user = userDataService.getByLogin(login);
//            if (user.isEmpty()) throw new Exception("Аккаунта не существует");
//            if (!user.get().getPass().equals(pass)) throw new Exception("Пароль неправильный");
//            if (user.get().getCreator() == null) throw new Exception("Вы не являетесь создателем");
//
//            Optional<Series> s = seriesCarsDataService.getById(series);
//            if(s.isEmpty()) throw new Exception("Серия не найдена");
//
//            Car car = new Car(user.get().getCreator(), name, s.get());
//
//            if(bumpers != null && !bumpers.equals("")) {
//                Optional<Bumper> b = bumpersDataService.getById(bumpers);
//                if (b.isEmpty()) throw new Exception("Бампер не найден");
//                car.setBumper(b.get());
//            }
//
//            if(wheels != null && !wheels.equals("")) {
//                Optional<Wheels> w = wheelsDataService.getById(wheels);
//                if (w.isEmpty()) throw new Exception("Колеса не найден");
//                car.setWheels(w.get());
//            }
//
//
//            if(first_color != null && !first_color.equals("")) {
//                Optional<Color> c1 = colorsDataService.getById(first_color);
//                if (c1.isEmpty()) throw new Exception("Первый цвет не найден");
//                car.setFirst_color(c1.get());
//            }
//
//
//            if(second_color != null && !second_color.equals("")) {
//                Optional<Color> c2 = colorsDataService.getById(second_color);
//                if (c2.isEmpty()) throw new Exception("Второй цвет не найден");
//                car.setSecond_color(c2.get());
//            }
//
//            seriesCarsDataService.saveCar(car);
//            return map;
//        } catch (Exception e) {
//            map.put("status", "error");
//            map.put("message", e.getMessage());
//            return map;
//        }
//    }
//
//    public Date convertToDateViaInstant(LocalDate dateToConvert) {
//        return Date.from(dateToConvert.atStartOfDay()
//                .atZone(ZoneId.systemDefault())
//                .toInstant());
//    }
}