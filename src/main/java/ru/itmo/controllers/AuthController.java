package ru.itmo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.entity.User;
import ru.itmo.services.UserDataService;

import java.util.Map;

@RestController
public class AuthController {
    private final UserDataService userDataService;

    @Autowired
    public AuthController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }


    @GetMapping("/api/v1/auth/reg")
    public Map<String, String> registration(@RequestParam("login") String login,
                                             @RequestParam("pass") String pass) {

        Map<String, String> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            if(userDataService.getByLogin(login).isEmpty()) {
                User user = new User(login, pass);
                userDataService.save(user);
            }
            else throw new Exception("Логин занят");
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }
}
