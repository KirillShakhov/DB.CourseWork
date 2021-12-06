package ru.itmo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.entity.Contract;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Реализация CRUD для Token
 */
@Repository
public interface CustomizedContractCrudRepository extends CrudRepository<Contract, Long> {
//    @Transactional
//    @Query(value = "select * from contracts where to_user = :username", nativeQuery = true)
//    List<Contract> findForUser(@Param("username") Long login);

    @Transactional
    @Query(value = "select * from contracts", nativeQuery = true)
    List<Contract> findAll();
}
