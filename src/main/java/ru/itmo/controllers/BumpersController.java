package ru.itmo.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.entity.*;
import ru.itmo.services.BumpersDataService;
import ru.itmo.services.ColorsDataService;
import ru.itmo.services.UserDataService;

import java.util.Map;
import java.util.Optional;

@RestController
public class BumpersController {
    private final BumpersDataService bumpersDataService;
    private final UserDataService userDataService;
    private final ColorsDataService colorsDataService;

    @Autowired
    public BumpersController(BumpersDataService bumpersDataService, UserDataService userDataService, ColorsDataService colorsDataService) {
        this.bumpersDataService = bumpersDataService;
        this.userDataService = userDataService;
        this.colorsDataService = colorsDataService;
    }

    @JsonView(View.Bumpers.class)
    @GetMapping("/api/v1/bumpers")
    public Map<String, Object> getBumpers() {
        Map<String, Object> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            map.put("list", bumpersDataService.findAll());
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @GetMapping("/api/v1/bumpers/create")
    public Map<String, String> createBumpers( @RequestParam("login") String login,
                                             @RequestParam("pass") String pass,
                                             @RequestParam("name") String name,
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
            Bumper bumper = new Bumper(user.get().getCreator_user(), name, photo, c.get());
            bumpersDataService.save(bumper);
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @GetMapping("/api/v1/bumpers/remove")
    public Map<String, String> removeBumpers( @RequestParam("login") String login,
                                             @RequestParam("pass") String pass,
                                             @RequestParam("id") Long id
    ) {
        Map<String, String> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            Optional<User> user = userDataService.getByLogin(login);
            if (user.isEmpty()) throw new Exception("???????????????? ???? ????????????????????");
            if (!user.get().getPass().equals(pass)) throw new Exception("???????????? ????????????????????????");

            Optional<Bumper> bumper = bumpersDataService.getById(id);
            if(bumper.isEmpty()) throw new Exception("???????????? ???? ????????????");
            if(!bumper.get().getCreator().getCreator_user().getUsername().equals(user.get().getUsername())) throw new Exception("???????????? ???????????? ???? ????????");
            bumpersDataService.removeById(bumper.get().getId_bumper());
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }
}