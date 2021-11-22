package ru.itmo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.entity.Car;

/**
 * Реализация CRUD для Token
 */
@Repository
public interface CustomizedCarsCrudRepository extends CrudRepository<Car, Long> {

}
