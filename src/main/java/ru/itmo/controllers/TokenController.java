package ru.itmo.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.entity.old.Token;
import ru.itmo.entity.View;
import ru.itmo.services.TokenDataService;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class TokenController {
    private final TokenDataService tokenDataService;

    @Autowired
    public TokenController(TokenDataService tokenDataService) {
        this.tokenDataService = tokenDataService;
    }

    @GetMapping("/admin/{secretKey:\\S+}/token/add")
    public String addToken(@PathVariable("secretKey") String secret_key, @RequestParam("pass") String pass, @RequestParam("token") String token, @RequestParam("tariff_id") Long tariff_id, @RequestParam(value = "user_name", required = false) String user_name) {
        try {
            String sk = "ventusaioonline";
            LocalDateTime dt = LocalDateTime.now();
            dt = dt.withSecond(0).withNano(0).plusMinutes((65 - dt.getMinute()) % 5);
            String skhash = sk + dt.toLocalTime().getMinute() + "" + dt.toLocalTime().getHour() + sk + dt.toLocalTime().getMinute() + "" + dt.toLocalTime().getHour();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(skhash.getBytes());
            byte[] digest = md.digest();
            String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
            System.out.println(myHash);
            if (myHash.equals(secret_key) && pass.equals("vvv")) {
                if (!token.isEmpty()) {
                    if (!tokenDataService.checkById(token)) {
                        Token token_new = new Token(token, user_name);
                        tokenDataService.save(token_new);
                        return "Ok";
                    } else {
                        return "Token busy";
                    }
                } else {
                    return "token is empty";
                }
            } else {
                return "Secret key not found";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @JsonView(View.Token.class)
    @GetMapping("/admin/{secretKey:\\S+}/token/show")
    public List<Token> getTokens(@PathVariable("secretKey") String secret_key, @RequestParam("pass") String pass) {
        try {
            String sk = "ventusaioonline";
            LocalDateTime dt = LocalDateTime.now();
            dt = dt.withSecond(0).withNano(0).plusMinutes((65 - dt.getMinute()) % 5);
            String skhash = sk + dt.toLocalTime().getMinute() + "" + dt.toLocalTime().getHour() + sk + dt.toLocalTime().getMinute() + "" + dt.toLocalTime().getHour();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(skhash.getBytes());
            byte[] digest = md.digest();
            String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
            System.out.println(myHash);
            if (myHash.equals(secret_key) && pass.equals("vvv")) {
                return tokenDataService.findAll();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/admin")
    public String admin() {
        return "Fake Admin Panel";
    }

    @GetMapping("/admin/{secretKey:\\S+}")
    public String admin(@PathVariable("secretKey") String secret_key) {
        return String.valueOf(Math.random() * 10000000L);
    }
}
