package ru.itmo.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.entity.View;
import ru.itmo.services.ColorsDataService;

import java.util.Map;

@RestController
public class ColorsController {
    private final ColorsDataService colorsDataService;

    @Autowired
    public ColorsController(ColorsDataService colorsDataService) {
        this.colorsDataService = colorsDataService;
    }

    @JsonView(View.Color.class)
    @GetMapping("/api/v1/colors")
    public Map<String, Object> getColors() {
        Map<String, Object> map = new ManagedMap<>();
        map.put("status", "ok");
        try {
            map.put("list", colorsDataService.findAll());
            return map;
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", e.getMessage());
            return map;
        }
    }
}