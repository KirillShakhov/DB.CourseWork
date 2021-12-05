package ru.itmo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.entity.Article;
import ru.itmo.repository.CustomizedArticlesCrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Основной сервис взаимодействия с H2
 * для Articles
 */

@Service
public class ArticlesDataService {
    private final CustomizedArticlesCrudRepository customizedArticlesCrudRepository;

    @Autowired
    public ArticlesDataService(CustomizedArticlesCrudRepository customizedArticlesCrudRepository) {
        this.customizedArticlesCrudRepository = customizedArticlesCrudRepository;
    }


    @Transactional
    public List<Article> findAll() {
        return (List<Article>) customizedArticlesCrudRepository.findAll();
    }

    @Transactional
    public void save(Article article) {
        customizedArticlesCrudRepository.save(article);
    }

    @Transactional
    public void removeById(Long id) {
        customizedArticlesCrudRepository.deleteById(id);
    }

    @Transactional
    public Optional<Article> getById(Long id) {
        return customizedArticlesCrudRepository.findById(id);
    }
}

