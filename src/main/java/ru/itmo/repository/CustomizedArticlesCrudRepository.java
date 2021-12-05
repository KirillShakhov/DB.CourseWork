package ru.itmo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.entity.Article;

/**
 * Реализация CRUD для Article
 */
@Repository
public interface CustomizedArticlesCrudRepository extends CrudRepository<Article, Long> {

}
