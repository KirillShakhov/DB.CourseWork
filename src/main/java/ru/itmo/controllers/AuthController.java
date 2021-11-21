package ru.itmo.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.entity.User;
import ru.itmo.entity.View;
import ru.itmo.services.UserDataService;

import java.util.Map;
import java.util.Optional;

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
            if (userDataService.getByLogin(login).isEmpty()) {
                User user = new User(login, pass);
                userDataService.save(user);
            } else throw new Exception("Логин занят");
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @GetMapping("/api/v1/auth")
    public Map<String, String> auth(@RequestParam("login") String login,
                                    @RequestParam("pass") String pass) {
        Map<String, String> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            if (userDataService.getByLogin(login).isEmpty()) throw new Exception("Аккаунта не существует");
            else if (!userDataService.getByLogin(login).get().getPass().equals(pass))
                throw new Exception("Пароль неправильный");
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @JsonView(View.User.class)
    @GetMapping("/api/v1/info")
    public Map<String, Object> info(@RequestParam("login") String login,
                                    @RequestParam("pass") String pass) {
        Map<String, Object> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            Optional<User> user = userDataService.getByLogin(login);
            if (user.isEmpty()) throw new Exception("Аккаунта не существует");
            else if (!user.get().getPass().equals(pass)) throw new Exception("Пароль неправильный");
            map.put("list", user.get());
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }
    @JsonView(View.User.class)
    @GetMapping("/api/v1/info/edit")
    public Map<String, Object> infoedit(@RequestParam("login") String login,
                                    @RequestParam("pass") String pass,
                                    @RequestParam("username") String username,
                                    @RequestParam("first_name") String first_name,
                                    @RequestParam("last_name") String last_name,
                                    @RequestParam("email") String email,
                                    @RequestParam("biography") String biography
                                    ) {
        Map<String, Object> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            Optional<User> user = userDataService.getByLogin(login);
            if (user.isEmpty()) throw new Exception("Аккаунта не существует");
            else if (!user.get().getPass().equals(pass)) throw new Exception("Пароль неправильный");
            User u = user.get();
            u.setBiography(biography);
            u.setUsername(username);
            u.setFirst_name(first_name);
            u.setLast_name(last_name);
            u.setEmail(email);
            userDataService.save(u);
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }
}