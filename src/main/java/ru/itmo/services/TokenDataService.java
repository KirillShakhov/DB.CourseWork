package ru.itmo.services;

import ru.itmo.entity.old.Token;
import ru.itmo.repository.CustomizedTokenCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Основной сервис взаимодействия с H2
 * для WebResource
 */

@Service
public class TokenDataService {
    private final CustomizedTokenCrudRepository customizedTokenCrudRepository;

    @Autowired
    public TokenDataService(CustomizedTokenCrudRepository customizedTokenCrudRepository) {
        this.customizedTokenCrudRepository = customizedTokenCrudRepository;
    }

    @Transactional
    public List<Token> findAll() {
        return (List<Token>) customizedTokenCrudRepository.findAll();
    }

    @Transactional
    public void save(Token token) {
        customizedTokenCrudRepository.save(token);
    }

    @Transactional
    public boolean checkById(String token) {
        return getById(token).isPresent()&&getById(token).get().isActive();
    }

    @Transactional
    public Optional<Token> getById(String token) { return customizedTokenCrudRepository.findById(token); }
}

