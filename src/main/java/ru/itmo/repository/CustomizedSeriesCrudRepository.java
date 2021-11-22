package ru.itmo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.entity.Series;

/**
 * Реализация CRUD для Token
 */
@Repository
public interface CustomizedSeriesCrudRepository extends CrudRepository<Series, Long> {

}
