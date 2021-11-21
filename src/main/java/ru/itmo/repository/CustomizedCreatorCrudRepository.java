package ru.itmo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.entity.Creator;

/**
 * Реализация CRUD для Token
 * */
@Repository
public interface CustomizedCreatorCrudRepository extends CrudRepository<Creator, String> {
}
