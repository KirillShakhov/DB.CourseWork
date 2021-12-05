package ru.itmo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.entity.Contract;

/**
 * Реализация CRUD для Token
 */
@Repository
public interface CustomizedContractCrudRepository extends CrudRepository<Contract, Long> {
//    @Transactional
//    @Query(value = "select * from purchase_items where owner = :username", nativeQuery = true)
//    List<Item> findByUser(@Param("username") Long login);
}
