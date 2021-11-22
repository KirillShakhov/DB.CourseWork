package ru.itmo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.entity.Wheels;

/**
 * Реализация CRUD для Token
 */
@Repository
public interface CustomizedWheelsCrudRepository extends CrudRepository<Wheels, Long> {

}
