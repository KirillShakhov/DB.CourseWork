package ru.itmo.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.entity.Series;
import ru.itmo.entity.User;
import ru.itmo.entity.View;
import ru.itmo.services.SeriesDataService;
import ru.itmo.services.UserDataService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
public class CarsController {
    private final SeriesDataService seriesDataService;
    private final UserDataService userDataService;

    @Autowired
    public CarsController(SeriesDataService seriesDataService, UserDataService userDataService) {
        this.seriesDataService = seriesDataService;
        this.userDataService = userDataService;
    }

    @JsonView(View.Series.class)
    @GetMapping("/api/v1/cars/groups")
    public Map<String, Object> getCarsGroup() {
        Map<String, Object> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            map.put("list", seriesDataService.findAll());
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @GetMapping("/api/v1/cars/groups/create")
    public Map<String, String> createBumpers( @RequestParam("login") String login,
                                             @RequestParam("pass") String pass,
                                             @RequestParam("name") String name,
                                             @RequestParam("description") String description,
                                             @RequestParam("date_of_start") String date_of_start,
                                             @RequestParam(value = "date_of_finish", required = false) String date_of_finish
                                             ) {
        Map<String, String> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            Optional<User> user = userDataService.getByLogin(login);
            if (user.isEmpty()) throw new Exception("Аккаунта не существует");
            if (!user.get().getPass().equals(pass)) throw new Exception("Пароль неправильный");
            if (user.get().getCreator() == null) throw new Exception("Вы не являетесь создателем");
            LocalDate date = LocalDate.parse(date_of_start);//"2018-05-05"
            Series series = new Series(user.get().getCreator(), name, description, convertToDateViaInstant(date));
            if(date_of_finish != null && date_of_finish.equals("")) series.setDate_of_finish(convertToDateViaInstant(LocalDate.parse(date_of_finish)));
            seriesDataService.save(series);
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }

    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
}