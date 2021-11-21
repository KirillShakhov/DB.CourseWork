package ru.itmo.repository;

import ru.itmo.entity.old.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Реализация CRUD для Token
 * */
@Repository
public interface CustomizedTokenCrudRepository extends CrudRepository<Token, String> {

}
