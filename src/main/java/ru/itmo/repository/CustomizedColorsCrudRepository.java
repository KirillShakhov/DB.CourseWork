package ru.itmo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.entity.Color;

/**
 * Реализация CRUD для Token
 * */
@Repository
public interface CustomizedColorsCrudRepository extends CrudRepository<Color, Long> {

}
