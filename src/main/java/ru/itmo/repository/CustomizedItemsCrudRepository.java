package ru.itmo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.entity.Item;

/**
 * Реализация CRUD для Token
 */
@Repository
public interface CustomizedItemsCrudRepository extends CrudRepository<Item, Long> {

}
