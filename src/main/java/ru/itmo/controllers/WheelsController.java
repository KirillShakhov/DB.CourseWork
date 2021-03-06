package ru.itmo.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.entity.*;
import ru.itmo.services.ColorsDataService;
import ru.itmo.services.UserDataService;
import ru.itmo.services.WheelsDataService;

import java.util.Map;
import java.util.Optional;

@RestController
public class WheelsController {
    private final WheelsDataService wheelsDataService;
    private final UserDataService userDataService;
    private final ColorsDataService colorsDataService;

    @Autowired
    public WheelsController(WheelsDataService wheelsDataService, UserDataService userDataService, ColorsDataService colorsDataService) {
        this.wheelsDataService = wheelsDataService;
        this.userDataService = userDataService;
        this.colorsDataService = colorsDataService;
    }

    @JsonView(View.Wheels.class)
    @GetMapping("/api/v1/wheels")
    public Map<String, Object> getWheels() {
        Map<String, Object> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            map.put("list", wheelsDataService.findAll());
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @GetMapping("/api/v1/wheels/create")
    public Map<String, String> createWheels( @RequestParam("login") String login,
                                             @RequestParam("pass") String pass,
                                             @RequestParam("name") String name,
                                             @RequestParam("cc") int cc,
                                             @RequestParam("photo") String photo,
                                             @RequestParam("color") Long color
                                             ) {
        Map<String, String> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            Optional<User> user = userDataService.getByLogin(login);
            if (user.isEmpty()) throw new Exception("???????????????? ???? ????????????????????");
            if (!user.get().getPass().equals(pass)) throw new Exception("???????????? ????????????????????????");
            if (user.get().getCreator_user() == null) throw new Exception("???? ???? ?????????????????? ????????????????????");
            Optional<Color> c =  colorsDataService.getById(color);
            if(c.isEmpty()) throw new Exception("???????????? ?????????? ??????");
            Wheels wheels = new Wheels(user.get().getCreator_user(), name, cc, photo, c.get());
            wheelsDataService.save(wheels);
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @GetMapping("/api/v1/wheels/remove")
    public Map<String, String> removeWheels( @RequestParam("login") String login,
                                              @RequestParam("pass") String pass,
                                              @RequestParam("id") Long id
    ) {
        Map<String, String> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            Optional<User> user = userDataService.getByLogin(login);
            if (user.isEmpty()) throw new Exception("???????????????? ???? ????????????????????");
            if (!user.get().getPass().equals(pass)) throw new Exception("???????????? ????????????????????????");

            Optional<Wheels> wheels = wheelsDataService.getById(id);
            if(wheels.isEmpty()) throw new Exception("???????????? ???? ??????????????");
            if(!wheels.get().getCreator().getCreator_user().getUsername().equals(user.get().getUsername())) throw new Exception("???????????? ?????????????? ???? ????????");
            wheelsDataService.removeById(wheels.get().getId_wheel());
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }
}