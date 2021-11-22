package ru.itmo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.entity.Bumper;

/**
 * Реализация CRUD для Token
 */
@Repository
public interface CustomizedBumpersCrudRepository extends CrudRepository<Bumper, Long> {

}
