package ru.itmo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itmo.entity.Item;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Реализация CRUD для Token
 */
@Repository
public interface CustomizedItemsCrudRepository extends CrudRepository<Item, Long> {
    @Transactional
    @Query(value = "select * from items where owner = :username", nativeQuery = true)
    List<Item> findByUser(@Param("username") Long login);
}
