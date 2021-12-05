package ru.itmo.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.entity.*;
import ru.itmo.services.ArticlesDataService;
import ru.itmo.services.SeriesCarsDataService;
import ru.itmo.services.UserDataService;

import java.util.Map;
import java.util.Optional;

@RestController
public class ArticlesController {
    private final ArticlesDataService articlesDataService;
    private final SeriesCarsDataService seriesCarsDataService;
    private final UserDataService userDataService;

    @Autowired
    public ArticlesController(ArticlesDataService articlesDataService, UserDataService userDataService, SeriesCarsDataService seriesCarsDataService) {
        this.articlesDataService = articlesDataService;
        this.seriesCarsDataService = seriesCarsDataService;
        this.userDataService = userDataService;
    }

    @JsonView(View.Article.class)
    @GetMapping("/api/v1/articles")
    public Map<String, Object> getArticles() {
        Map<String, Object> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            map.put("list", articlesDataService.findAll());
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @GetMapping("/api/v1/articles/create")
    public Map<String, String> createArticles( @RequestParam("login") String login,
                                             @RequestParam("pass") String pass,
                                             @RequestParam("title") String title,
                                             @RequestParam("text") String text,
                                             @RequestParam(value = "car", required = false) Long car
                                             ) {
        Map<String, String> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            Optional<User> user = userDataService.getByLogin(login);
            if (user.isEmpty()) throw new Exception("Аккаунта не существует");
            if (!user.get().getPass().equals(pass)) throw new Exception("Пароль неправильный");
            Article article = new Article(title, text, user.get());
            if (car != null) {
                Optional<Car> c = seriesCarsDataService.getCarById(car);
                if(c.isEmpty()) throw new Exception("Машинка не найдена");
                article.setCar(c.get());
            }
            articlesDataService.save(article);
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }

    @GetMapping("/api/v1/articles/remove")
    public Map<String, String> removeArticle( @RequestParam("login") String login,
                                           @RequestParam("pass") String pass,
                                           @RequestParam("id") Long id
    ) {
        Map<String, String> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            Optional<User> user = userDataService.getByLogin(login);
            if (user.isEmpty()) throw new Exception("Аккаунта не существует");
            if (!user.get().getPass().equals(pass)) throw new Exception("Пароль неправильный");

            Optional<Article> article = articlesDataService.getById(id);
            if(article.isEmpty()) throw new Exception("Статья не найден");
            if(!article.get().getAuthor().getId_user().equals(user.get().getId_user())) throw new Exception("Статья написана не вами");
            articlesDataService.removeById(article.get().getId_articles());
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }
}