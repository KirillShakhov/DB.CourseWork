package ru.itmo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itmo.entity.User;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Реализация CRUD для Token
 * */
@Repository
public interface CustomizedUserCrudRepository extends CrudRepository<User, Long> {
    @Transactional
    @Query(value = "select * from users where username = :username", nativeQuery = true)
    Optional<User> findByLogin(@Param("username") String login);
}
